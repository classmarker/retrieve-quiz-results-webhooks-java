
# ClassMarker.com
## Retrieve Quiz Results Webhooks in Java

ClassMarker is a secure online Quiz Maker platform for Business and Education for giving exams and assessments.

Our WEBHOOKS allow you to receive Quiz results in real time using Java.

# How to Create a Webhook to receive Quiz results in Java
https://www.classmarker.com/online-testing/manual/#api_webhooks

# Developer Documentation
https://www.classmarker.com/online-testing/api/webhooks/




# Testing Webhooks  

**Note:** You must add your unique SECRET PHRASE which you are given when you create your Webhook in ClassMarker.
**See:** 'classmarkerSecretPhrase' in the **WebhookServlet.java** file.

You can test your Webhook script is working by sending sample Webhooks to your endpoint URL from your Edit Webhooks page in ClassMarker.

 #### *The difference between LIVE Webhooks and VERIFICATION Webhooks:*
The **payload_status** element in the JSON body will read:
...{
"payload_status", "live"  // For live actual User results
},...
**OR**
...{
"payload_status", "verify"  // When Verifying from your Webhooks page
},...

 # Testing Webhooks locally
For testing locally, you create a secure URL to your localhost using: https://ngrok.com/


# Getting Started

### STEP 1 - Optional pending your requirements

 Create a database to store results in.
 A sample table scheme is available at: [Github ClassMarker API](https://github.com/classmarker/API-PHP-MYSQL-SAMPLE-CODE) (Question and Answer tables are not included).
 See: **create_classmarker_tables.txt**
 * *classmarker_tests:*  			for tests information (test name and ID)
 * *classmarker_groups:* 	 	for group information (group name and ID)
 * *classmarker_links:*  			for link information (link name and ID)
 * *classmarker_group_results:* 	for group results (holds test results taken within Groups)
 * *classmarker_link_results:*  	for link results (holds test results taken from Direct links)


### STEP 2 - Using Docker on Localhost

- Create a new Webhook in ClassMarker under the My account / Webhooks page.
- Set Endpoint Url: 'http://localhost' (we'll change this later)
- Then click 'Save Webhook Settings'
- Click 'View Webhook secret phase'
- Copy your secret phrase to the **classmarkerSecretPhrase** variable in **WebhookServlet.java** file
- Build the docker image via 'docker build -t classmarkerjavaexample .'
- Run the docker image via 'docker run -p 8080:8080 classmarkerjavaexample'
- Use ngrok to tunnel verification Webhooks from ClassMarker to your localhost
- Run ngrok to open publicly accessible port to your classmarkerjavaexample webserver via './ngrok http 8080' which will give you a http and a https url.
- On your Edit Webhooks page in ClassMarker, Update the Endpoint URL: set to '[HTTPS URL FROM NGROK]/webhook'
- Select the checkbox 'Verify on Save'.
- Then click 'Save webhook settings' to test.
- You should see the message 'HTTP RESPONSE STATUS CODE: 200'.


### STEP 3
Recommended to log webhooks to a local file to see you are receiving webhooks OK.


### Disclaimer  

ClassMarker Pty Ltd accepts no responsibility whatsoever from usage of these scripts and shall be held harmless from any and all litigation, liability, and responsibilities.
