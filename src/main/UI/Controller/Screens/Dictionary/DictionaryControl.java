package main.UI.Controller.Screens.Dictionary;
import java.util.ArrayList;
import java.util.Map;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.ScrollPane.ScrollBarPolicy;
import javafx.scene.control.TextField;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.Server.API.DictionaryAPI;
import main.Server.API.TTSAPI;
import main.Server.Database.DictionaryManager.WordManager.Word;
import main.Server.Database.DictionaryManager.WordManager.ExtensionManager.ContributionManager.WordContribute;
import main.UI.Components.SearchBar.SearchBar;
import main.UI.Controller.Alerts.ContributeAlert;
import main.UI.Controller.Alerts.DeleteAlert;
import main.UI.Controller.Screens.ScreenControl;
import main.UI.Controller.Screens.Topic.TopicControl;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;

public class DictionaryControl extends ScreenControl {
    private static String target;

    public static void setTarget(String _target) {
        target = _target;
    }

    public static String getTarget() {
        return target;
    }

    @FXML
    private Rectangle rectangle;
    @FXML
    private TextField searchField;
    @FXML
    private VBox suggestedList;
    @FXML
    private AnchorPane anchorPane;
    @FXML
    private Text word;
    @FXML
    private Text phonetic;
    @FXML
    private VBox explainBox;
    @FXML
    private ScrollPane scrollPane;
    @FXML
    private Button speaker;
    @FXML
    private Button addButton;
    @FXML 
    private Button editButton;

    private SearchBar searchBar;
    private Word wordData = new Word();

    @Override
    public void decor() {
        searchBar = new SearchBar(anchorPane, rectangle, searchField, suggestedList);
        searchBar.getClass();
        this.getWordDefinition();

        scrollPane.setHbarPolicy(ScrollBarPolicy.NEVER);
        scrollPane.setVbarPolicy(ScrollBarPolicy.AS_NEEDED);
        scrollPane.setFitToWidth(true);

        scrollPane.setStyle("-fx-background-color: white; -fx-fill: white;");
        explainBox.setStyle("-fx-background-color: white; -fx-fill: white;");
    }

    private void getWordDefinition() {
        Font semiboldFont = Font.font("Montserrat SemiBold", FontWeight.NORMAL, 14);
        Font italicFont = Font.font("Montserrat Italic", FontWeight.NORMAL, 14);
        Font regularFont = Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, 13);
        Font lightFont = Font.font(Font.getDefault().getFamily(), FontWeight.LIGHT, 18);

        wordData.setWordTarget(target);

        if (!WordContribute.isUserWord(target)) {
            DictionaryAPI.fetchDefinition(wordData);
        } else {
            WordContribute.lookUpUserWord(wordData);
        }

        word.setText(target);

        phonetic.setFont(lightFont);
        phonetic.setText(wordData.getWordPhonetic());

        explainBox.getChildren().clear();
        explainBox.setSpacing(15);

        Map<String, ArrayList<String>> wordExplain = wordData.getWordExplain();

        for (String pos : wordData.getWordPartOfSpeech()) {
            Text partOfSpeech = new Text(pos);
            partOfSpeech.setFont(italicFont);
            explainBox.getChildren().add(partOfSpeech);

            for (String e : wordExplain.get(pos)) {
                Text explain = new Text(e);
                explain.setWrappingWidth(explainBox.getPrefWidth());
                if (e.charAt(0) == '•') {
                    explain.setFont(semiboldFont);
                } else {
                    explain.setFont(regularFont);
                }
                explainBox.getChildren().add(explain);
            }
        }
    }

    @FXML
    private void playSound() {
        TTSAPI.playEn(wordData.getWordTarget());
    }

    private static boolean topicFrom = false;

    public static void setTopicFrom(boolean _topicFrom) {
        topicFrom = _topicFrom;
    }

    @FXML
    private void back() {
        if (topicFrom) {
            TopicControl.setTopicName(TopicControl.getTopicName());
            super.loadScreenFXML("Topic/topic");
        } else {
            super.homeStarted();
        }
    }

    @FXML
    private void deleteAlert() {
        DeleteAlert.setTarget(target);
        super.loadAlertFXML("delete");
    }

    @FXML
    private void contributeAlert(ActionEvent event) {
        Button sourcedButton = (Button) event.getSource();
        super.loadAlertFXML("contribute");
        if (sourcedButton.equals(addButton)) {
            ContributeAlert.setContributeType("addWord");
        } else if (sourcedButton.equals(editButton)) {
            ContributeAlert.setContributeType("editWord");
        } 
    }
}