package main.Server.Database.DictionaryManager.WordManager.ExtensionManager.ContributionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.util.Pair;

import main.Server.Database.DatabaseManager;
import main.Server.Database.DictionaryManager.WordManager.Word;

public class WordContribute extends DatabaseManager {
    public static boolean getPermission(int wordID) {
        final String sql_query = "SELECT * FROM user_permission WHERE user_id = ? AND word_id = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            ps.setInt(1, user.getId());
            ps.setInt(2, wordID);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    return rs.next();
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }
    
    public static int getWordID(String target) {
        final String sql = "SELECT id FROM dictionary WHERE target = ?;";
        int id = -1;
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, target);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        id = rs.getInt("id");
                    }
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return id;
    }

    public static boolean checkExist(String target) {
        final String sql_query = "SELECT target FROM dictionary WHERE target = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            ps.setString(1, target);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    return rs.next();
                } finally {
                    close(rs);
                } 
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return true;
    }

    public static ArrayList<Pair<String, String>> lookUpUserWord(Word word) {
        String target = word.getWordTarget();
        ArrayList<Pair<String, String>> res = new ArrayList<>();
        final String sql_qury = "SELECT * FROM user_edits WHERE target = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql_qury);
            ps.setString(1, target);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        String partOfSpeech = rs.getString("partOfSpeech");
                        String definition = rs.getString("definition");
                        String[] explain = definition.split("\n");
                        word.addWordPartOfSpeech(partOfSpeech);
                        word.addWordExplain(partOfSpeech, explain[0]);
                        word.addWordExplain(partOfSpeech, explain[1]);
                    }
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return res;
    }

    public static boolean isUserWord(String target) {
        final String sql_query = "SELECT * FROM user_permission WHERE target = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            ps.setString(1, target);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    return rs.next();
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void getUserEdit(Word word) {      
        String target = word.getWordTarget();  
        final String sql_query = "SELECT partOfSpeech, definition FROM user_permission NATURAL JOIN user_edits WHERE user_id = ? AND target = ?";

        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            ps.setInt(1, DatabaseManager.getUser().getId());
            ps.setString(2, target);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        String partOfSpeech = rs.getString("partOfSpeech");
                        String definition = rs.getString("definition");
                        String[] explain = definition.split("\n");
                        word.addWordPartOfSpeech(partOfSpeech);
                        word.addWordExplain(partOfSpeech, explain[0]);
                        word.addWordExplain(partOfSpeech, explain[1]);
                    }
                } finally {
                    close(rs);
                }
            } finally {
                close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}