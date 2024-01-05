package main.Server.Database.DictionaryManager.WordManager.ExtensionManager.ContributionManager;

import java.sql.PreparedStatement;
import java.sql.SQLException;

import main.Server.Database.DictionaryManager.Trie;
import main.Server.Database.DictionaryManager.WordManager.ExtensionManager.WordHistory;

public class WordDelete extends WordContribute {
    public static boolean delete(String target) {
        int id = getWordID(target);
        if (!getPermission(id)) {
            return false;
        }

        if (!deleteDefinition(target)) {
            return false;
        }

        if (!deletePermission(id)) {
            return false;
        }

        if (!deleteFromDatabase(target)) {
            return false;
        }

        WordHistory.removeFromHistory(target);
        return true;
    }

    public static boolean deleteIfZeroDefinition(String target) {
        int id = getWordID(target);
        if (!getPermission(id)) {
            return false;
        }
        
        if (!deletePermission(id)) {
            return false;
        }

        if (!deleteFromDatabase(target)) {
            return false;
        }

        WordHistory.removeFromHistory(target);
        return true;
    }

    private static boolean deleteFromDatabase(String target) {
        final String sql_que = "DELETE FROM dictionary WHERE target = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql_que);
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
        }
        Trie.deleteWord(target);

        return true;
    }

    private static boolean deleteDefinition(String target) {
        final String sql = "DELETE FROM user_edits WHERE target = ?;";
        try {
            PreparedStatement p = connection.prepareStatement(sql);
            p.setString(1, target);
            try {
                int rowsAffected = p.executeUpdate();
                if (rowsAffected <= 0) {
                    return false;
                }
            } finally {
                close(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    private static boolean deletePermission(int wordID) {
        final String sql_query = "DELETE FROM user_permission WHERE word_id = ? AND user_id = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            ps.setInt(1, wordID);
            ps.setInt(2, WordContribute.user.getId());
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