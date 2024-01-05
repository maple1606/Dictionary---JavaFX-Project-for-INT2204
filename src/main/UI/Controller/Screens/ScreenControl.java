package main.UI.Controller.Screens;

import javafx.fxml.FXML;
import main.UI.Controller.FXMLControl;
import main.UI.Controller.Screens.Dictionary.DictionaryControl;

public abstract class ScreenControl extends FXMLControl {
    public static ScreenControl getScreen() {
        return screen;
    }

    @FXML
    public void homeStarted() {
        super.loadScreenFXML("Dashboard/dashboard");
    }

    @FXML
    public void exploreStarted() {
        super.loadScreenFXML("Explore/explore");
    }

    @FXML
    public void gameStarted() {
        super.loadScreenFXML("Game/selectGame");
    }

    @FXML
    public void translateStarted() {
        super.loadScreenFXML("Translation/translation");
    }

    @FXML
    public void navigate() {
        super.loadScreenFXML("LogIn/signIn");
    }

    @FXML
    public void dictionaryStarted(String target) {
        DictionaryControl.setTarget(target);
        super.loadScreenFXML("Dictionary/dictionary");
    }
}