package com.classmarker.webhook;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Date;
import java.util.Objects;
import java.util.Enumeration;
import java.util.stream.Collectors;
import java.util.Base64;

@WebServlet("/webhook")
public class WebhookServlet extends HttpServlet {

    @Override
    protected void doPost(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws IOException {
        System.out.println("received webhook request");
        
        // You are given a unique secret code when creating a Webhook.
        String classmarkerSecretPhrase = "classmarker_secret_phrase";
		
        String jsonStringPayload = httpServletRequest.getReader().lines().collect(Collectors.joining(System.lineSeparator()));
        String headerHmacSignature = httpServletRequest.getHeader("x-classmarker-hmac-sha256");

        boolean isVerifiedSuccessfully = false;

        try {
            isVerifiedSuccessfully = verifyWebhook(jsonStringPayload, headerHmacSignature, classmarkerSecretPhrase);
        } catch (InvalidKeyException | NoSuchAlgorithmException exception) {
             // handle exception here			 
        }
        
        if (isVerifiedSuccessfully) {
            // Save results in your database.
            // Important: Do not use a script that will take a long time to respond.

            // Notify ClassMarker you have received the Webhook.
            System.out.println("200");
            httpServletResponse.setStatus(HttpServletResponse.SC_OK);
        }
        else{
            System.out.println("400");
            httpServletResponse.setStatus(HttpServletResponse.SC_BAD_REQUEST);
        }

    }

    private boolean verifyWebhook(String jsonData, String headerHmacSignature, String classmarkerSecretPhrase) throws InvalidKeyException, NoSuchAlgorithmException {
        byte[] hashHMacBytes = hashHMac(jsonData, classmarkerSecretPhrase);
        String calculatedSignature = Base64.getEncoder().encodeToString(hashHMacBytes);
        
        return Objects.equals(headerHmacSignature, calculatedSignature);
    }

    private byte[] hashHMac(String sourceString, String key) throws InvalidKeyException, NoSuchAlgorithmException {
        String HMAC_SHA256 = "HmacSHA256";
        Mac sha512HMAC = Mac.getInstance(HMAC_SHA256);

        byte[] keyBytes = key.getBytes(StandardCharsets.US_ASCII);

        SecretKeySpec secretKeySpec = new SecretKeySpec(keyBytes, HMAC_SHA256);

        sha512HMAC.init(secretKeySpec);

        return sha512HMAC.doFinal(sourceString.getBytes(StandardCharsets.US_ASCII));
    }
}
