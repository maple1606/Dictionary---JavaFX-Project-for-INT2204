package main.UI.Controller.Screens.Explore;

import java.util.ArrayList;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import main.Server.Database.NewsfeedManager.FeedBox;
import main.Server.Database.NewsfeedManager.Newsfeed;
import main.UI.Components.DynamicField.FeedField;
import main.UI.Components.SearchBar.SearchBar;
import main.UI.Controller.Alerts.ContributeAlert;
import main.UI.Controller.Screens.ScreenControl;

public class ExploreControl extends ScreenControl {
    @FXML 
    private Button communityButton;
    @FXML
    private Button yoursButton;
    @FXML
    private VBox vBox;
    @FXML
    private Rectangle rectangle;
    @FXML
    private TextField searchField;
    @FXML
    private VBox suggestedList;
    @FXML
    private AnchorPane anchorPane;

    private SearchBar searchBar;

    protected ArrayList<FeedField> feedFields = new ArrayList<>();

    @FXML
    private void contributeAlert(ActionEvent event) {
        super.loadAlertFXML("contribute");
        ContributeAlert.setContributeType("addWord");
    }

    @FXML
    private void communityStarted() {
        communityButton.setStyle("-fx-text-fill: #FF4D56;");
        yoursButton.setStyle("-fx-text-fill: black;");

        vBox.getChildren().clear();

        for (FeedBox fb : Newsfeed.getAllEdit()) {
            FeedField f = new FeedField(fb, feedFields, vBox);
            vBox.getChildren().add(f.getContainerPane());
        };
    }

    @FXML
    private void yoursStarted() {
        yoursButton.setStyle("-fx-text-fill: #FF4D56;");
        communityButton.setStyle("-fx-text-fill: black;");

        vBox.getChildren().clear();

        for (FeedBox fb : Newsfeed.getUserEdit()) {
            FeedField f = new FeedField(fb, feedFields, vBox);
            vBox.getChildren().add(f.getContainerPane());
        };
    }

    @Override
    public void decor() {
        searchBar = new SearchBar(anchorPane, rectangle, searchField, suggestedList);
        searchBar.getClass();
        communityStarted();
    }
}
