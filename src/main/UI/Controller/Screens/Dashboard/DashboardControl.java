package main.UI.Controller.Screens.Dashboard;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import main.Server.Database.UserManaer.User;
import main.UI.Components.Leaderboard.Leaderboard;
import main.UI.Components.SearchBar.SearchBar;
import main.UI.Components.TopicIntroduction.TopicIntroduction;
import main.UI.Controller.Screens.ScreenControl;
import main.UI.Controller.Screens.Topic.TopicControl;

public class DashboardControl extends ScreenControl {
    @FXML
    protected Text greeting;
    @FXML
    protected Text usrId;
    @FXML
    protected Rectangle rectangle;
    @FXML
    protected TextField searchField;
    @FXML
    protected VBox suggestedList;
    @FXML
    protected AnchorPane anchorPane;
    @FXML
    private TableView<User> leaderboardTable;
    @FXML
    private Button practice;
    @FXML
    private Label topicName;
    @FXML
    private TextFlow topicDescription;
    @FXML
    private ImageView topicAvatar;
    @FXML
    private Button left;
    @FXML
    private Button right;

    protected SearchBar searchBar;
    private TopicIntroduction tIntro;

    public void decor() {
        tIntro = new TopicIntroduction(topicName, topicDescription, topicAvatar);
        tIntro.show();
        Leaderboard l = new Leaderboard(leaderboardTable);
        l.show();
        searchBar = new SearchBar(anchorPane, rectangle, searchField, suggestedList);
        String _greeting = "Good day, " + usr.getDisplayName();
        greeting.setText(_greeting);
        String _usrId = "User ID: " + usr.getId();
        usrId.setText(_usrId);
    }

    @FXML
    private void toLeft() {
        tIntro.toLeft();
    }

    @FXML
    private void toRight() {
        tIntro.toRight();
    }

    @FXML
    private void practiceStarted() {
        TopicControl.setTopicName(topicName.getText());
        TopicControl.setWordIndex(0);
        super.loadScreenFXML("Topic/topic");
    }
}