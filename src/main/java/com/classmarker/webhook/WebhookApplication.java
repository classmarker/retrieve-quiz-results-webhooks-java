package com.classmarker.webhook;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import javax.servlet.ServletContextListener;
import java.util.Arrays;

@SpringBootApplication
public class WebhookApplication {

    public static void main(String[] args) {
		SpringApplication.run(WebhookApplication.class, args);
	}

	@Bean
    ServletRegistrationBean myServletRegistration () {
        ServletRegistrationBean srb = new ServletRegistrationBean();
        srb.setServlet(new WebhookServlet());
        srb.setUrlMappings(Arrays.asList("/webhook/*"));
        return srb;
    }

}
