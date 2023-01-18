package org.zkoss.support;


import java.io.*;
import java.net.*;
import java.util.Properties;

/**
 * https://github.com/TheoKanning/openai-java
 * https://github.com/PlexPt/chatgpt-java
 */
public class ChatService {

    static final String API_KEY = loadApiKey();
    private HttpURLConnection con;

    public ChatService() {
        initHttpURLConnection();
    }

    public String prompt(String message) {
        try {
            sendRequest(message);
            return readResponse().toString();
        } catch (IOException e) {
            return e.toString();
        }
    }


    private StringBuffer readResponse() throws IOException {
        int responseCode = this.con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(this.con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();
        return response;
    }

    private void sendRequest(String prompt) throws IOException {
        String requestBody = "{\"prompt\": \"" + prompt + "\", \"temperature\": 0.5, \"max_tokens\": 2048}";
        if (!con.getDoOutput()){
            con.setDoOutput(true);
        }
        DataOutputStream wr = new DataOutputStream(this.con.getOutputStream());
        wr.writeBytes(requestBody);
        wr.flush();
        wr.close();
    }

    private void initHttpURLConnection() {
        URL url = null;
        HttpURLConnection con = null;
        try {
            url = new URL("https://api.openai.com/v1/engines/davinci-codex/completions");
            this.con = (HttpURLConnection) url.openConnection();
            this.con.setRequestMethod("POST");
            this.con.setRequestProperty("Content-Type", "application/json");
            this.con.setRequestProperty("Authorization", "Bearer " + API_KEY);
            System.out.println("key: " + API_KEY);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    private static String loadApiKey() {
        Properties properties = new Properties();
        try {
            properties.load(ChatService.class.getResource("/config.properties").openStream());
            if (properties.containsKey("api.key")) {
                return properties.getProperty("api.key");
            }
            throw new IllegalStateException("no api key found in config.properties");
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) throws IOException {
        System.out.println(new ChatService().prompt("hello"));
//        test();
    }

    public static void test() throws IOException {
        // Set up the request parameters
        URL url = new URL("https://api.openai.com/v1/engines/davinci-codex/completions");
        HttpURLConnection con = (HttpURLConnection) url.openConnection();
        con.setRequestMethod("POST");
        con.setRequestProperty("Content-Type", "application/json");
        con.setRequestProperty("Authorization", "Bearer " + API_KEY);

        // Build the request body
//            String prompt = "What is the weather like in San Francisco today?";
        String prompt = "hello";
//            String requestBody = "{\"model\": \"text-davinci-003\",\"prompt\": \"" + prompt + "\", \"temperature\": 0.5, \"max_tokens\": 2048}";
        String requestBody = "{\"prompt\": \"" + prompt + "\", \"temperature\": 0.5, \"max_tokens\": 2048}";

        // Send the request
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(con.getOutputStream());
        wr.writeBytes(requestBody);
        wr.flush();
        wr.close();

        // Read the response
        int responseCode = con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        in.close();

        // Print the response
        System.out.println(response.toString());
    }
}
