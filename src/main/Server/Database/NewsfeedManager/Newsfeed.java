package main.Server.Database.NewsfeedManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Server.Database.DatabaseManager;

public class Newsfeed extends DatabaseManager {
    private static int limit = 20;

    public static ArrayList<FeedBox> getAllEdit() {
        ArrayList<FeedBox> boxList = new ArrayList<>();
        
        final String sql_query = "SELECT * FROM user_permission NATURAL JOIN user_edits ORDER BY edit_id DESC LIMIT ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            ps.setInt(1, limit);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        int userID = rs.getInt("user_id");
                        String target = rs.getString("target");
                        String partOfSpeech = rs.getString("partOfSpeech");
                        String definition = rs.getString("definition");
                        FeedBox feedBox = new FeedBox(userID, target, partOfSpeech, definition);
                        boxList.add(feedBox);
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
        return boxList;
    }

    public static ArrayList<FeedBox> getUserEdit() {
        ArrayList<FeedBox> boxList = new ArrayList<>();
        
        final String sql_query = "SELECT * FROM user_permission NATURAL JOIN user_edits WHERE user_id = ? ORDER BY edit_id DESC LIMIT ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            ps.setInt(1, DatabaseManager.getUser().getId());
            ps.setInt(2, limit);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        int userID = rs.getInt("user_id");
                        String target = rs.getString("target");
                        String partOfSpeech = rs.getString("partOfSpeech");
                        String definition = rs.getString("definition");
                        FeedBox feedBox = new FeedBox(userID, target, partOfSpeech, definition);
                        boxList.add(feedBox);
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
        return boxList;
    }
    
}