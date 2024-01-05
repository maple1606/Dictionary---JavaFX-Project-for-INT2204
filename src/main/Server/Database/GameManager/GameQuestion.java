package main.Server.Database.GameManager;

import java.util.HashSet;
import java.util.Random;
import java.util.Set;

import main.Constants;
import main.Server.Database.DatabaseManager;

public abstract class GameQuestion extends DatabaseManager {
    protected String[][] options = new String[Constants.quesNum][Constants.maxGameOption];
    protected String[] correct = new String[Constants.quesNum];

    public abstract void setQuestionContent();

    public static int[] randomSeed() {
        int[] idxList = new int[10];
        int idx = 0;
        Random random = new Random();
        Set<Integer> uniqueNumbers = new HashSet<>();
        uniqueNumbers.clear();

        while (uniqueNumbers.size() < 10) {
            int randomNumber = random.nextInt(40); 
            if (!uniqueNumbers.contains(randomNumber)) {
                uniqueNumbers.add(randomNumber);
                idxList[idx++] = randomNumber;
            }
        }
        return idxList;
    }
   
    public void setOptions(int id, int choice, String optContent) {
        options[id][choice] = optContent;
    }

    public String getOptions(int id, int choice) {
        return this.options[id][choice];
    }    

    public void setCorrect(int id, String correct) {
        this.correct[id] = correct;
    }

    public String getCorrect(int id) {
        return this.correct[id];
    }
}