package main.Server.Database.DictionaryManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import main.Server.Database.DatabaseManager;

public class Dictionary extends DatabaseManager {
    private static ArrayList<String> prefixList = new ArrayList<>();

    public static ArrayList<String> getPrefixList() {
        return prefixList;
    }

    public static void lookUpWord(String targetPrefix, int searchLimit) {
        prefixList.clear();
        ArrayList<String> matches = Trie.searchWords(targetPrefix, searchLimit);
        prefixList.addAll(matches);
    }

    public static void trieStruct() {
        final String sql_query = "SELECT target FROM dictionary";
        try {
            PreparedStatement ps = connection.prepareStatement(sql_query);
            try {
                ResultSet rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        String target = rs.getString("target");
                        Trie.insert(target);
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
