package main.Server.Database.DictionaryManager.WordManager.ExtensionManager.ContributionManager;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javafx.util.Pair;
import main.Server.Database.DictionaryManager.Trie;

public class WordAdd extends WordContribute {
    public static boolean add(String target, ArrayList<Pair<String, String>> definition) {
        if (checkExist(target)) {
            return false;
        }
        
        if (!insertTargetIntoDatabase(target)) {
            return false;
        }

        int id = getWordID(target);
        if (!setPermission(id, target)) {
            return false;
        }

        for (Pair<String, String> pair : definition) {
            String key = pair.getKey();
            String value = pair.getValue();
            if (!setDefinition(target, key, value)) {
                return false;
            }
        }
        return true;
    }

    private static boolean insertTargetIntoDatabase(String target) {
        final String sql_query = "INSERT INTO dictionary(target) VALUES(?);";

        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            ps.setString(1, target);
            try {
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected <= 0) {
                    return false;
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }

        Trie.insert(target);
        return true;
    }

    private static boolean setPermission(int wordID, String target) {
        final String sql_query = "INSERT INTO user_permission(user_id, word_id, target) VALUES(?, ?, ?);";

        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            ps.setInt(1, WordContribute.user.getId());
            ps.setInt(2, wordID);
            ps.setString(3, target);
            try {
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected <= 0) {
                    return false;
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static boolean setDefinition(String target, String partOfSpeech, String definition) {
        final String fi_sql_query = "INSERT INTO user_edits(target, partOfSpeech, definition) VALUES(?, ?, ?);";

        try {
            PreparedStatement ps = connection.prepareStatement(fi_sql_query);
            ps.setString(1, target);
            ps.setString(2, partOfSpeech);
            ps.setString(3, definition);
            try {
                int rowsAffected = ps.executeUpdate();
                if (rowsAffected <= 0) {
                    return false;
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }
}