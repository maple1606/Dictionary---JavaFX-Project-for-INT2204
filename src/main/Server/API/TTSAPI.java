// reference: https://github.com/HynDuf/dictionary/blob/ui/src/main/java/dictionary/server/TextToSpeech.java

package main.Server.API;

import java.net.URLEncoder; 
import java.nio.charset.StandardCharsets;

public class TTSAPI extends APIManager {
    private static String text;

    public static void playEn(String _text) {
        text = _text;
        play("en");
    }

    public static void playVi(String _text) {
        text = _text;
        play("vi");
    }

    private static void play(String lang) {
        try {
            String API_URL =
                    "https://translate.google.com/translate_tts?ie=UTF-8&tl="
                            + lang
                            + "&client=tw-ob&q="
                            + URLEncoder.encode(text, StandardCharsets.UTF_8);
            APIManager.setAPIUrl(API_URL);
            APIManager.getAudioResponse();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}