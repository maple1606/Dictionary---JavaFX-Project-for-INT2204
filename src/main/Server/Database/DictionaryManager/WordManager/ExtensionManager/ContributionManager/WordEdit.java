package main.Server.Database.DictionaryManager.WordManager.ExtensionManager.ContributionManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class WordEdit extends WordContribute {
    public static boolean saveDefinition(String target,
            String oldPartOfSpeech, String newPartOfSpeech,
            String oldDefinition, String newDefinition) {
        int id = getWordID(target);
        if (!getPermission(id)) {
            return false;
        }

        if (isSaved(target, oldPartOfSpeech, oldDefinition)) {
            return editDefinition(target, oldPartOfSpeech, oldDefinition, newPartOfSpeech, newDefinition);
        } else {
            return addDefinition(target, newPartOfSpeech, newDefinition);
        }
    }

    public static int getNumberOfSavedDefinitions(String target) {
        final String sql_query = "SELECT COUNT(*) FROM user_edits WHERE target = ?;";
    
        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            ps.setString(1, target);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        return rs.getInt(1);
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
        return 0;
    }

    private static boolean addDefinition(String target, String partOfSpeech, String definition) {
        int id = getWordID(target);
        if (!getPermission(id)) {
            return false;
        }
        final String sql_query = "INSERT INTO user_edits(target, partOfSpeech, definition) VALUES(?, ?, ?);";
        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
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

    public static boolean removeDefinition(String target, String partOfSpeech, String definition) {
        int id = getWordID(target);
        if (!getPermission(id)) {
            return false;
        }
        final String sql_query = "DELETE FROM user_edits WHERE target = ? AND partOfSpeech = ? AND definition = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
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
        if (cantExist(target)) {
            WordDelete.deleteIfZeroDefinition(target);
        }

        return true;
    }

    private static boolean isSaved(String target, String partOfSpeech, String definition) {
        final String sql_query = "SELECT * FROM user_edits WHERE target = ? AND partOfSpeech = ? AND definition = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            ps.setString(1, target);
            ps.setString(2, partOfSpeech);
            ps.setString(3, definition);
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

    public static boolean editDefinition(String target, String oldPartOfSpeech, String oldDefinition,
        String newPartOfSpeech, String newDefinition) {
        final String query = "UPDATE user_edits SET partOfSpeech = ?, definition = ? WHERE target = ? AND partOfSpeech = ? AND definition = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(query);
            ps.setString(1, newPartOfSpeech);
            ps.setString(2, newDefinition);
            ps.setString(3, target);
            ps.setString(4, oldPartOfSpeech);
            ps.setString(5, oldDefinition);
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

    private static boolean cantExist(String target) {
        final String sql = "SELECT * FROM user_edits WHERE target = ?;";
        try {
            PreparedStatement ps = connection.prepareStatement(sql);
            ps.setString(1, target);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    if (!rs.next()) {
                        return true;
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
        return false;
    }
}