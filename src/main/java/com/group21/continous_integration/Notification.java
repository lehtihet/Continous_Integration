package com.group21.continous_integration;

import org.json.JSONObject;
import org.json.JSONException;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


public class Notification {
	//TO make it work you need a personal token

	public int SendStatus (String sendToUrl, String sha, String status, String token) throws Exception{

        if(token.length() != 40)
            throw new Exception("Invalid or unset personal access token. See run.sh file!");

		URL url = new URL (sendToUrl + sha);
		HttpURLConnection con;
		
        con = (HttpURLConnection)url.openConnection();
        con.setRequestMethod("POST");
        con.setDoOutput(true);
        con.setRequestProperty("Authorization", "token " + token);
        con.addRequestProperty("repo", sendToUrl + sha);
        con.addRequestProperty("Content-Type", "application/json");
        con.addRequestProperty("Accept", "application/json");

        JSONObject message = new JSONObject();
        message.put("state", status);
        message.put("target_url", sendToUrl + sha);
        message.put("description", "Build " + status);
        message.put("context", "continuous-integration");

        String jsonInputString = message.toString();

        OutputStream os = con.getOutputStream();
        byte[] input = jsonInputString.getBytes();

        os.write(input);
        os.flush();
        os.close();

        int code = con.getResponseCode();

        BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8"));
        StringBuilder response = new StringBuilder();
        String responseLine = null;
        while ((responseLine = br.readLine()) != null) {
            response.append(responseLine.trim());
        }

        return code;
	}
}
