package main.UI.Components.Leaderboard;

import java.util.ArrayList;

import javafx.scene.control.TableView;
import javafx.scene.layout.Background;
import javafx.scene.layout.BackgroundFill;
import javafx.scene.paint.Color;
import main.Server.Database.UserManaer.User;
import main.Server.Database.UserManaer.UserLeaderboard;
import main.UI.Components.ComponentManager;

public class Leaderboard implements ComponentManager {
    private TableView<User> leaderboardTable;
    private ArrayList<User> leaderboard = new ArrayList<>();

    public Leaderboard(TableView<User> leaderboardTable) {
        this.leaderboardTable = leaderboardTable;
        this.initialize();
    }

    @Override
    public void initialize() {
        leaderboard = UserLeaderboard.getLeaderboard(); 
    }

    public void show() {
        double rowHeight = 41.8; 
        leaderboardTable.setFixedCellSize(rowHeight);

        int numRows = 10; 
        double tableHeight = (numRows + 1) * rowHeight; 
        leaderboardTable.setPrefHeight(tableHeight);

        leaderboardTable.getItems().clear();

        leaderboardTable.setBackground(
                new Background(new BackgroundFill(
                        Color.TRANSPARENT, null, null)));

        leaderboardTable.setStyle("-fx-font-size: 11px;");
        leaderboardTable.getStyleClass().add("noheader");
        leaderboardTable.getStyleClass().add("noborder");

        leaderboardTable.getItems().addAll(leaderboard);
    }
}