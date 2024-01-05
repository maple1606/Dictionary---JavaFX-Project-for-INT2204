package main.Server.Database.DictionaryManager.WordManager.ExtensionManager;

import java.util.ArrayList;
import java.util.Collections;

public class WordHistory {
    private static ArrayList<String> historyList = new ArrayList<>();
    private static int sizeLimit = 10;

    public static void addToHistory(String target) {
        historyList.removeIf(e -> e.equals(target));
        historyList.add(target);
        refactor();
    }

    public static void removeFromHistory(String target) {
        historyList.removeIf(e -> e.equals(target));
    }

    private static void refactor() {
        int n = historyList.size();
        if (n > sizeLimit) {
            historyList.remove(0);
        }
    }

    public static ArrayList<String> getHistory() {
        ArrayList<String> newHistoryList = new ArrayList<>(historyList);
        Collections.reverse(newHistoryList);
        return newHistoryList;
    }
}