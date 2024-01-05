package main.UI.Controller.Screens.Game;

import javafx.fxml.FXML;
import main.UI.Controller.Screens.ScreenControl;

public class SelectGame extends ScreenControl {
    @FXML 
    private void choiceStarted() {
        super.loadScreenFXML("Game/choiceGame");
    }

    @FXML 
    private void orderStarted() {
        super.loadScreenFXML("Game/orderGame");
    }
}   
