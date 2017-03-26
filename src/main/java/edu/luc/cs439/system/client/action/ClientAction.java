package edu.luc.cs439.system.client.action;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

public class ClientAction {

    public String Get(URL url, String contentType) throws IOException {
        HttpURLConnection conn = (HttpURLConnection) url.openConnection();
        try {
            conn.setDoOutput(true);
            conn.setRequestMethod("GET");
            conn.setRequestProperty("Content-Type", contentType);


            if (conn.getResponseCode() < 200 || conn.getResponseCode() > 299) {
                throw new RuntimeException("Failed : HTTP error code : " + conn.getResponseCode());
            }

            BufferedReader br = new BufferedReader(new InputStreamReader((conn.getInputStream())));

            StringBuffer buff = new StringBuffer();

            String output;
            while ((output = br.readLine()) != null) {
                buff.append(output);
                buff.append('\n');
            }

            return buff.toString();
        } finally {
            conn.disconnect();
        }
    }
}