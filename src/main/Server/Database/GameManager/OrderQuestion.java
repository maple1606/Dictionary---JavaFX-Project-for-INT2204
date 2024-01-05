package main.Server.Database.GameManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.Constants;
import main.Server.Database.DatabaseManager;

public class OrderQuestion extends GameQuestion {
    private String str = "";

    public OrderQuestion() {
        setQuestionContent();
    }

    @Override
    public void setQuestionContent() {
        for (int id = 0; id < Constants.quesNum; id++) {
            final String sql_query = "SELECT * FROM order_questions_content NATURAL JOIN order_options WHERE ques_id = ?;";
            try {
                PreparedStatement ps = connection.prepareStatement(sql_query);
                ps.setInt(1, id + 1);
                try {
                    ResultSet rs = ps.executeQuery();
                    try {
                        String tmp = "";

                        while (rs.next()) {
                            String optionColumn = "option";
                            String tmpOptionColumn = optionColumn;
                            for (int i = 1; i <= 6; i++) {
                                tmpOptionColumn += (char) (i + '0');
                                // System.out.println(tmpOptionColumn);
                                String option = rs.getString(tmpOptionColumn);
                                tmpOptionColumn = optionColumn;
                                // System.out.println(choice);

                                super.setOptions(id, i - 1, option);
                                // System.out.print(id + " ");
                                // System.out.print(i - 1 + " ");
                                // System.out.println(option);
                            }
                            tmp = rs.getString("correct_ans");
                        }
                        String cor = "";
                        cor += tmp;
                        cor += " ";
                        super.setCorrect(id, cor);
                        // System.out.println(cor);

                    } finally {
                        DatabaseManager.close(rs);
                    }
                } finally {
                    DatabaseManager.close(ps);
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void setStr(String str) {
        this.str = str;
    }

    public String getStr() {
        return this.str;
    }

    public String addString(String addStr) {
        this.str += addStr;
        this.str += " ";
        return this.str;
    }

    public String substractString() {
        this.str = this.str.substring(0, this.str.length() - this.getLastWord().length() - 1);
        return this.str;
    }

    public boolean correct(int id) {
        return this.str.equals(super.getCorrect(id));
    }

    public int countWords() {
        if (this.str.isEmpty()) 
            return 0;
        else {
            String[] words = this.str.split("\\s+"); // Split the string using whitespace as delimiter
            return words.length;
        }
    }

    public String getLastWord() {
        String[] words = this.str.split("\\s+");
        if (words.length > 0) {
            return words[words.length - 1];
        } else {
            return "";
        }
    }

    public int countWords(int id) {
        String key = super.getCorrect(id);
        if (key.isEmpty()) 
            return 0;
        else {
            String[] words = key.split("\\s+"); // Split the string using whitespace as delimiter
            return words.length;
        }
    }
}