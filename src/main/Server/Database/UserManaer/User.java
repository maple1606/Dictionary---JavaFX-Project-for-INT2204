package main.Server.Database.UserManaer;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.Server.Database.DatabaseManager;

public class User extends DatabaseManager {
    private String displayName;
    private String password;
    private int score;
    private int id;

    public User() {
        
    }

    public User(String displayName, String password, int score, int id) {
        this.displayName = displayName;
        this.password = password;
        this.score = score;
        this.id = id;
    }

    public String signIn(String displayName, String password) {
        final String sql_query = "SELECT * FROM info WHERE user_displayname = ? AND user_password = ?;";

        try {
            PreparedStatement p = connection.prepareStatement(sql_query);
            p.setString(1, displayName);
            p.setString(2, password);
            try {
                ResultSet r = p.executeQuery();
                try {
                    if (r.next()) {
                        id = r.getInt("user_id");
                        score = r.getInt("score");
                        this.setDisplayName(displayName);
                        this.setPassword(password);
                        this.setScore(score);
                        this.setId(id);
                        return "Signed in successfully!";
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
        return "Invalid username or password.";
    }

    public String signUp(String displayName, String password) {

        final String sql_que = "SELECT * FROM info WHERE user_displayname = ?;";

        try {
            PreparedStatement ps = connection.prepareStatement(sql_que);
            ps.setString(1, displayName);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    if (rs.next()) {
                        return "Username has already existed.";
                    }
                } finally {
                    DatabaseManager.close(rs);
                }
            } finally {
                DatabaseManager.close(ps);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        final String sql_query = "INSERT INTO info(user_displayname, user_password, score) VALUES(?, ?, ?);";

        try {
            PreparedStatement p = connection.prepareStatement(sql_query);
            p.setString(1, displayName);
            p.setString(2, password);
            p.setInt(3, 0);
            try {
                p.executeUpdate();
                return "Signed up successffully!";
            } catch (SQLException e) {
                e.printStackTrace();
            } finally {
                DatabaseManager.close(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return "Signed up failed.";
    }

    public void logOut() {
        this.displayName = null;
        this.password = null;
        this.score = 0;
        this.id = 0;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getDisplayName() {
        return this.displayName;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPassword() {
        return this.password;
    }

    public void setScore(int score) {
        this.score = score;
    }

    public int getScore() {
        return this.score;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return this.id;
    }

    public void updateScore(int bonus) {
        this.score += bonus;

        final String sql_query = "UPDATE info SET score = ? WHERE user_displayname = ?;";

        try {
            PreparedStatement p = connection.prepareStatement(sql_query);
            p.setInt(1, this.score);
            p.setString(2, this.displayName);
            try {
                p.executeUpdate();
            } finally {
                DatabaseManager.close(p);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}