package main.Server.API;

import org.json.JSONArray;
import org.json.JSONObject;

import main.Server.Database.DictionaryManager.WordManager.Word;

public class DictionaryAPI extends APIManager {
    public static void fetchDefinition(Word word) {
        try {
            String target = word.getWordTarget();
            String API_URL = "https://api.dictionaryapi.dev/api/v2/entries/en/" + target;

            APIManager.setAPIUrl(API_URL);
            String response = APIManager.getTextResponse();
            JSONArray jsonArray = new JSONArray(response);
            JSONObject entry = jsonArray.getJSONObject(0);
            if (entry.has("word")) {
                JSONArray phonetics = entry.getJSONArray("phonetics");
                if (phonetics.length() > 0) {
                    JSONObject firstPhonetic = phonetics.getJSONObject(0);
                    if (firstPhonetic.has("text")) {
                        word.setWordPhonetic(firstPhonetic.getString("text"));
                    }
                }
                for (int i = 0; i < jsonArray.length(); i++) {
                    entry = jsonArray.getJSONObject(i);

                    JSONArray meanings = entry.getJSONArray("meanings");
                    for (int j = 0; j < meanings.length(); j++) {
                        JSONObject meaning = meanings.getJSONObject(j);
                        if (meaning.has("partOfSpeech")) {
                            word.addWordPartOfSpeech(meaning.getString("partOfSpeech"));
                        }
                        String partOfSpeech = meaning.getString("partOfSpeech");
                        JSONArray definitions = meaning.getJSONArray("definitions");
                        for (int k = 0; k < definitions.length(); k++) {
                            JSONObject definition = definitions.getJSONObject(k);
                            if (definition.has("definition")) {
                                String definitionExplain = "• " + definition.getString("definition");
                                word.addWordExplain(partOfSpeech, definitionExplain);
                            }
                            if (definition.has("example")) {
                                String exampleExplain = definition.getString("example");
                                word.addWordExplain(partOfSpeech, "    ◦ " + exampleExplain);
                            }
                        }
                    }
                }
            } else {
                // System.out.println("Target not found.");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
