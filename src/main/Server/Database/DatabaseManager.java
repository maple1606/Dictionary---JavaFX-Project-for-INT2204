package main.Server.Database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.Server.Database.DictionaryManager.Dictionary;
import main.Server.Database.UserManaer.User;

public abstract class DatabaseManager {
    protected static User user;
    
    public static void setUser(User _user) {
        user = _user;
    }

    public static User getUser() {
        return user;
    }

    public static void initialize() {
        loadDatabase();
    }
    
    protected static Connection connection = null;

    protected static Connection getConnection() {
        return connection;
    }

    protected static void close(Connection connection) {
        try {
            if (connection != null) {
                connection.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
 
    protected static void close(PreparedStatement ps) {
        try {
            if (ps != null) {
                ps.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
     }
 
    protected static void close(ResultSet rs) {
        try {
            if (rs != null) {
                rs.close();
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    private static void loadDatabase() {
        final String DATABASE_NAME = "Dictionary";
        final String HOST_NAME = "localhost";
        final String USER_NAME = "root";
        final String PASSWORD = "";
        final String PORT = "3306";
        final String MYSQL_URL = "jdbc:mysql://" + HOST_NAME + ":" + PORT + "/" + DATABASE_NAME;

        System.out.println("Connecting to database...");
        try {
            connection = DriverManager.getConnection(MYSQL_URL, USER_NAME, PASSWORD);
            System.out.println("Database connected!\n");
 
        } catch (SQLException e) {
            e.printStackTrace();
        }
        Dictionary.trieStruct();
    }
}