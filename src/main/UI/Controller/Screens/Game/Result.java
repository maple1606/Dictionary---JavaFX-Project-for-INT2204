package main.UI.Controller.Screens.Game;

import javafx.fxml.FXML;
import javafx.scene.text.Text;
import main.UI.Controller.Screens.ScreenControl;

public class Result extends ScreenControl {
    private static int result;

    public static void setResult(int _result) {
        result = _result;
    }
 
    @FXML private Text score;
    @FXML private Text total;

    public void decor() {
        score.setText("Score: " + String.valueOf(result) + "/100");
        usr.updateScore(result);
        total.setText("Total: " + String.valueOf(usr.getScore()));
    }
}
