package main.Server.API;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import javazoom.jl.player.Player;

public abstract class APIManager {
    private static String API_URL;

    public static void setAPIUrl(String apiUrl) {
        API_URL = apiUrl;
    }
    
    protected static String getTextResponse() {
        int responseCode = 0;
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");
            responseCode = conn.getResponseCode();
            StringBuilder response = new StringBuilder();
            BufferedReader in = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            String inputLine;
            while ((inputLine = in.readLine()) != null) {
                response.append(inputLine);
            }
            in.close();
            return response.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return "HTTP request failed with response code: " + String.valueOf(responseCode);
        }
    }

    protected static Player getAudioResponse() {
        try {
            URL url = new URL(API_URL);
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            InputStream audio = conn.getInputStream();
            new Player(audio).play();
            conn.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }
}