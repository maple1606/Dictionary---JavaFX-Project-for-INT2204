package main.Server.Database.DictionaryManager.WordManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Word {
    private String wordTarget;
    private String wordPhonetic;
    private ArrayList<String> wordPartOfSpeech = new ArrayList<>();
    private Map<String, ArrayList<String>> wordExplain = new HashMap<>();
    private boolean addedByUsers = false;

    public String getWordTarget() {
        return this.wordTarget;
    }

    public void setWordTarget(String wordTarget) {
        this.wordTarget = wordTarget;
    }

    public ArrayList<String> getWordPartOfSpeech() {
        return this.wordPartOfSpeech;
    }

    public void setWordPartOfSpeech(ArrayList<String> wordPartOfSpeech) {
        this.wordPartOfSpeech = wordPartOfSpeech;
    }

    public void addWordPartOfSpeech(String partOfSpeech) {
        if (!this.wordExplain.containsKey(partOfSpeech)) {
            this.wordPartOfSpeech.add(partOfSpeech);
        }
    }

    public Map<String, ArrayList<String>> getWordExplain() {
        return this.wordExplain;
    }

    public void setWordExplain(Map<String, ArrayList<String>> wordExplain) {
        this.wordExplain = wordExplain;
    }

    public void addWordExplain(String partOfSpeech, String wordExplain) {
        if (!this.wordExplain.containsKey(partOfSpeech)) {
            this.wordExplain.put(partOfSpeech, new ArrayList<>());
        }

        this.wordExplain.get(partOfSpeech).add(wordExplain);
    }

    public String getWordPhonetic() {
        return this.wordPhonetic;
    }

    public void setWordPhonetic(String wordPhonetic) {
        this.wordPhonetic = wordPhonetic;
    }

    public void setAddedByUsers(boolean addedByUsers) {
        this.addedByUsers = addedByUsers;
    }

    public boolean isAddedByUsers() {
        return this.addedByUsers;
    }
}
