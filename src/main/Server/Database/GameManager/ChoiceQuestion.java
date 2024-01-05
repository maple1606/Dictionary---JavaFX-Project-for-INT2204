package main.Server.Database.GameManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import main.Constants;
import main.Server.Database.DatabaseManager;

public class ChoiceQuestion extends GameQuestion {
    protected String[] questions = new String[Constants.quesNum];

    public ChoiceQuestion() {
        setQuestionContent();
    }

    public String getQuestion(int id) {
        return this.questions[id];
    }

    public void setQuestion(int id, String quesContent) {
        this.questions[id] = quesContent;
    }

    @Override
    public void setQuestionContent() {
        for (int id = 0; id < Constants.quesNum; id++) {
            final String sql_query = "SELECT * FROM multi_questions_content NATURAL JOIN multi_questions WHERE ques_id = ?;";
            try {
                PreparedStatement ps = connection.prepareStatement(sql_query);
                ps.setInt(1, id + 1);
                try {
                    ResultSet rs = ps.executeQuery();
                    try {
                        String qu = "";
                        String tmp = "";

                        while (rs.next()) {
                            String ans_id = rs.getString("ans_id");
                            char c = ans_id.charAt(0);
                            
                            int choice;
                            try {
                                choice = (int) c;
                            } catch (NumberFormatException e) {
                                choice = -1; 
                            }
                            choice -= 65;
                            // System.out.println(choice);

                            String answer = rs.getString("ans_content");
                            super.setOptions(id, choice, answer);

                            qu = rs.getString("ques_content");
                            tmp = rs.getString("correct_ans");

                            // System.out.print(id + " ");
                            // System.out.print(c + " ");
                            // System.out.println(answer);
                        }
                        String cor = "";
                        cor += tmp;
                        // System.out.println(qu);
                        this.setQuestion(id, qu);
                        super.setCorrect(id, cor);

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
}