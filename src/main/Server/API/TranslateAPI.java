// reference: https://github.com/HynDuf/dictionary/blob/ui/src/main/java/dictionary/server/TranslatorApi.java

package main.Server.API;

import java.io.IOException;
import java.net.URLEncoder;

public class TranslateAPI extends APIManager {
    private final static String en = "en";
    private final static String vi = "vi";

    public static String toVi(String text) {
        return translate(en, vi, text);
    }

    public static String toEn(String text) {
        return translate(vi, en, text);
    }

    private static String translate(String langFrom, String langTo, String text) {
        try {
            String API_URL = "https://script.google.com/macros/s/AKfycbzgVNssi5uYdhSAkF5S-t63hJQFVLYLGWD6oilloKX76GmTxQRHgNZarqpGNW-q7Abt/exec"
                    +
                    "?q=" + URLEncoder.encode(text, "UTF-8") +
                    "&target=" + langTo +
                    "&source=" + langFrom;
            APIManager.setAPIUrl(API_URL);
            return APIManager.getTextResponse();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return "Request failed";
    }
} 