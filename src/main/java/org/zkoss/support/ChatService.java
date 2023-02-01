package org.zkoss.support;






import com.google.gson.*;
import org.zkoss.json.*;

import java.io.*;
import java.net.*;
import java.util.Properties;

/**
 * Create completion: https://platform.openai.com/docs/api-reference/completions/create
 */
public class ChatService {

    static final String API_KEY = loadApiKey();
    private HttpURLConnection con;
    private URL url;

    public ChatService() {
        try {
            url = new URL("https://api.openai.com/v1/completions");
        }catch (MalformedURLException e){
            throw new IllegalStateException(e);
        }
    }

    public String prompt(String message) {
        try {
            sendRequest(message);
            return readResponse().trim().replace("\n\n", "\n");
        } catch (IOException e) {
            e.printStackTrace();
            return e.toString();
        }
    }


    private String readResponse() throws IOException {
        int responseCode = this.con.getResponseCode();
        BufferedReader in = new BufferedReader(new InputStreamReader(this.con.getInputStream()));
        String inputLine;
        StringBuffer response = new StringBuffer();
        while ((inputLine = in.readLine()) != null) {
            response.append(inputLine);
        }
        JsonParser parser = new JsonParser();
        JsonElement jsonElement = parser.parse(response.toString());
        JsonObject jsonObject = jsonElement.getAsJsonObject();
        JsonArray jsonArray = jsonObject.getAsJsonArray("choices");
        JsonObject firstChoice = jsonArray.get(0).getAsJsonObject();

        in.close();
        con.disconnect();
        return firstChoice.get("text").getAsString();
    }

    private void sendRequest(String prompt) throws IOException {
        initHttpURLConnection();
        String requestBody = "{\"model\": \"text-davinci-003\", \"prompt\": \"" + prompt + "\", \"temperature\": 0.5, \"max_tokens\": 2048}";
        con.setDoOutput(true);
        DataOutputStream wr = new DataOutputStream(this.con.getOutputStream());
        wr.writeBytes(requestBody);
        wr.flush();
        wr.close();
    }

    private void initHttpURLConnection() {
        try {
            this.con = (HttpURLConnection) url.openConnection();
            this.con.setRequestMethod("POST");
            this.con.setRequestProperty("Content-Type", "application/json");
            this.con.setRequestProperty("Authorization", "Bearer " + API_KEY);
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
}
