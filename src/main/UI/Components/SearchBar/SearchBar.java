package main.UI.Components.SearchBar;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import main.Server.Database.DictionaryManager.Dictionary;
import main.Server.Database.DictionaryManager.WordManager.ExtensionManager.WordHistory;
import main.UI.Components.ComponentManager;
import main.UI.Controller.Screens.ScreenControl;
import main.UI.Controller.Screens.Dashboard.DashboardControl;
import main.UI.Controller.Screens.Dictionary.DictionaryControl;

public class SearchBar implements ComponentManager {
    private Rectangle rectangle;
    private TextField searchBar;
    private VBox suggestedList;
    private AnchorPane anchorPane;
    private ScreenControl screen;
    private static ArrayList<String> history = new ArrayList<>();
    private static ArrayList<String> prefixList = new ArrayList<>();
    private final static int searchLimit = 10;

    public SearchBar(AnchorPane anchorPane, Rectangle rectangle, TextField searchBar, VBox suggestedList) {
        this.anchorPane = anchorPane;
        this.rectangle = rectangle;
        this.searchBar = searchBar;
        this.suggestedList = suggestedList;
        screen = ScreenControl.getScreen();
        this.initialize();
    }
    
    @Override
    public void initialize() {
        searchBar.setEditable(false);
        searchBar.setOnKeyReleased(this::handleKeyPressed);
        searchBar.setOnMouseClicked(this::searchWord);
        anchorPane.setOnMousePressed(this::quitSearch);
    }

    private void addHistoryToSearch() {
        history.clear();
        String target = searchBar.getText();
        for (String word : WordHistory.getHistory()) {
            if (word.startsWith(target) || target.isEmpty()) {
                history.add(word);
            }
        }
    }

    private void searchWord(MouseEvent event) {
        prefixList.clear();
        addHistoryToSearch();

        rectangle.setStyle("");
        rectangle.getStyleClass().add("grey-box");
        if (!searchBar.isEditable()) {
            searchBar.setEditable(true);
            showPrefixList();
        }
    }

    private void handleKeyPressed(KeyEvent event) {
        if (searchBar.isEditable() == true) {
            prefixList.clear();
            suggestedList.getChildren().clear();

            addHistoryToSearch();
            Dictionary.lookUpWord(searchBar.getText(), searchLimit - history.size());
            prefixList.addAll(Dictionary.getPrefixList());
            showPrefixList();
        }
    }

    private void showPrefixList() {
        suggestedList.setSpacing(12);
        int historySz = history.size();
        prefixList.addAll(0, history);
        double h = 92;
        for (String w : prefixList) {
            HBox prefixBox = new HBox(6);
            prefixBox.setAlignment(Pos.CENTER);
            prefixBox.getStyleClass().add("transparent-box");
    
            Button prefix = new Button();
            prefix.getStyleClass().add("transparent-box");
            prefix.setPrefWidth(searchBar.getPrefWidth());
            prefix.setText(w);
    
            prefixBox.setPadding(new Insets(4, 0, 4, 12));
            prefix.setOnMouseClicked(this::wordShow);
    
            ImageView icon;

            if (historySz > 0) {
                icon = new ImageView(new Image("resources/icons/clock.png"));
                historySz--;
            } else {
                icon = new ImageView(new Image("resources/icons/search.png"));
            }
            icon.setFitHeight(21);
            icon.setFitWidth(21);
    
            prefixBox.getChildren().add(icon);
            prefixBox.getChildren().add(prefix);
            suggestedList.getChildren().add(prefixBox);
            h += 47.5;
        }
        rectangle.setHeight(h);
    }

    private void wordShow(MouseEvent event) {
        Button selectedButton = (Button) event.getSource();
        String target = selectedButton.getText();
        WordHistory.addToHistory(target);
        if (screen instanceof DashboardControl) {
            DictionaryControl.setTopicFrom(false);
        }
        screen.dictionaryStarted(target);
    }

    private void quitSearch(MouseEvent event) {
        suggestedList.getChildren().clear();
        searchBar.setEditable(false);
        searchBar.setFocusTraversable(false);
        rectangle.setHeight(0);
        rectangle.getStyleClass().remove("grey-box");
        rectangle.setStyle("-fx-fill: transparent");
    }
}