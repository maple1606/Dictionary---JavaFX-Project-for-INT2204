package main.Server.Database.DictionaryManager;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Trie {
    private final static TrieNode root = new TrieNode('\0');

    public static void insert(String word) {
        TrieNode current = root;
        for (char ch : word.toCharArray()) {
            TrieNode node = current.children.get(ch);
            if (node == null) {
                node = new TrieNode(ch);
                current.children.put(ch, node);
            }
            current = node;
        }
        current.isEndOfWord = true;
    }
    
    public static ArrayList<String> searchWords(String prefix, int limit) {
        ArrayList<String> matches = new ArrayList<>();
        TrieNode current = root;

        for (char c : prefix.toCharArray()) {
            if (!current.children.containsKey(c)) {
                return null; 
            }
            current = current.children.get(c);
        }

        dfs(current, prefix, matches, limit);
        return matches;
    }
    
    public static void deleteWord(String word) {
        TrieNode current = root;
        TrieNode previous = null;
        char ch;

        for (int i = 0; i < word.length(); i++) {
            ch = word.charAt(i);
            if (!current.children.containsKey(ch)) {
                return;
            }
            previous = current;
            current = current.children.get(ch);
        }

        if (!current.isEndOfWord) {
            return;
        }

        if (current.children.isEmpty()) {
            previous.children.remove(current.character);
        } else {
            current.isEndOfWord = false;
        }
    }

    private static void dfs(TrieNode node, String word, List<String> matches, int limit) {
        if (matches.size() >= limit) {
            return; 
        }

        if (node.isEndOfWord) {
            matches.add(word);
        }

        for (TrieNode child : node.children.values()) {
            dfs(child, word + child.character, matches, limit);
        }
    }

    private static class TrieNode {
        Map<Character, TrieNode> children;
        boolean isEndOfWord;
        char character;

        private TrieNode(char ch) {
            character = ch;
            children = new HashMap<>();
            isEndOfWord = false;
        }
    }
}