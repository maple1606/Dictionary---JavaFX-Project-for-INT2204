package main.Server.Database.UserManaer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Server.Database.DatabaseManager;

public class UserLeaderboard extends DatabaseManager {
    private static final int limit = 10;
    private static ArrayList<User> leaderboard = new ArrayList<>();
    
    public static void setLeaderboard() {
        leaderboard.clear();
        final String sql_query = "SELECT * FROM info ORDER BY score DESC LIMIT ?;";
        try {
            PreparedStatement p = connection.prepareStatement(sql_query);
            p.setInt(1, limit);
            try {
                ResultSet r = p.executeQuery();
                try {
                    while (r.next()) {
                        String displayName = r.getString("user_displayname");
                        String password = r.getString("user_password");
                        int score = r.getInt("score");
                        int id = r.getInt("user_id");

                        User user = new User(displayName, password, score, id);
                        leaderboard.add(user);
                    }
                } finally {
                    DatabaseManager.close(r);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DatabaseManager.close(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<User> getLeaderboard() {
        setLeaderboard();
        return leaderboard;
    }
}