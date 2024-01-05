package main.Server.Database.NewsfeedManager;

import main.Server.Database.DatabaseManager;

public class FeedBox {
    private int userID;
    private String wordTarget;
    private String partOfSpeech;
    private String definition;

    public FeedBox(int userID, String wordTarget, String partOfSpeech, String definition) {
        this.userID = userID;
        this.wordTarget = wordTarget;
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
    }

    public FeedBox(String wordTarget, String partOfSpeech, String definition) {
        this.userID = DatabaseManager.getUser().getId();
        this.wordTarget = wordTarget;
        this.partOfSpeech = partOfSpeech;
        this.definition = definition;
    }

    public int getUserID() {
        return this.userID;
    }

    public String getWordTarget() {
        return this.wordTarget;
    }

    public String getPartOfSpeech() {
        return this.partOfSpeech;
    }

    public String getDefinition() {
        return this.definition;
    }
}