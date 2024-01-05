package main.Server.Database.DictionaryManager.WordManager.ExtensionManager;

import main.Server.Database.DatabaseManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import javafx.util.Pair;

public class WordTopic extends DatabaseManager{
    private String topicName;
    private String topicImage;
    private String topicDescrition;
    private int topicIndex;
    private static ArrayList<String> topicList = new ArrayList<>();
    private ArrayList<Pair<String, String>> wordList = new ArrayList<>();
    
    public String getTopicName() {
        return this.topicName;
    }

    public String getTopicImage() {
        return this.topicImage;
    }

    public String getTopicDescription() {
        return this.topicDescrition;
    }

    public void setTopic(String name) {
        this.topicName = name;
        setTopicDetail(name);
    }

    public ArrayList<Pair<String, String>> getWordList() {
        return this.wordList;
    }

    public String getTarget(int idx) {
        return this.wordList.get(idx).getKey();
    }

    public String getURL(int idx) {
        return this.wordList.get(idx).getValue();
    }

    public int getTopicIndex() {
        return this.topicIndex;
    }

    public static ArrayList<String> getTopicList() {
        final String sql_query = "SELECT topic_name FROM topic";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    while(rs.next()) {
                        topicList.add(rs.getString("topic_name"));
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
        return topicList;
    }

    private void setTopicDetail(String name) {
        final String sql_query = "SELECT t.topic_id, t.topic_name, t.topic_image, t.description, d.target, t.word_image FROM dictionary d INNER JOIN (SELECT * FROM topic NATURAL JOIN topic_detail WHERE topic.topic_name = ?) t ON d.target = t.target;";
        
        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            ps.setString(1, name);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    while(rs.next()) {
                        this.topicImage = rs.getString("topic_image");
                        this.topicDescrition = rs.getString("description");
                        this.topicIndex = rs.getInt("topic_id");
                        String target = rs.getString("target");
                        String image = rs.getString("word_image");
                        this.wordList.add(new Pair<String,String>(target, image));
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