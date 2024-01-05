package main.UI.Controller.Screens.Game;

import javafx.animation.KeyFrame;
import javafx.animation.PauseTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import javafx.util.Duration;
import main.Server.Database.GameManager.GameQuestion;
import main.UI.Controller.Screens.ScreenControl;

public abstract class GameControl extends ScreenControl {
    @FXML
    protected Text timerLabel;
    @FXML
    protected Text index;

    @FXML
    protected ImageView gameMascot;
    protected String mascot;

    protected void timer() {
        timeline = new Timeline(new KeyFrame(Duration.seconds(1), event -> {
            timerLabel.setText(formatTime());
            seconds--;
            if (checkTimer()) {
                showResult();
                timeline.stop(); // Stop the timeline when the timer reaches 0
            }
        }));
        timeline.setCycleCount(Timeline.INDEFINITE);
        timeline.play();
    }

    protected abstract void selectOption(ActionEvent event);

    protected int idx;
    protected int[] idxList = new int[10];
    protected int res = 0;
    private int seconds = 100;
    private static Timeline timeline;

    protected boolean checkTimer() {
        if (seconds <= 0) return true;
        else return false;
    }
 
    protected String formatTime() {
        int minutes = seconds / 60;
        int remainingSeconds = seconds % 60;
        return String.format(minutes + "m" + remainingSeconds + "s");
    }

    protected void showResult() {
        Result.setResult(res);
        super.loadScreenFXML("Game/result");
    }
    
    protected abstract boolean isAnswerCorrect(String selectedAns);
    protected abstract void showOption(int index);
    protected abstract void showAnswer(String styleClass);
    protected abstract void resetOptions(String styleClass);
    protected abstract void gamePlay();

    protected void changeStyleClass(Button button, String oldStyleClass, String newStyleClass) {
        button.getStyleClass().remove(oldStyleClass);
        button.getStyleClass().add(newStyleClass);
    }

    protected void setImage(String styleClass) {
        if (styleClass == "correct") {
            gameMascot.setImage(new Image("resources/image/" + mascot + "Correct.png"));
        } else if (styleClass == "wrong") {
            gameMascot.setImage(new Image("resources/image/" + mascot + "Wrong.png"));
        }
    }

    protected void processAnswer(String answer) {
        String styleClass;
        if (isAnswerCorrect(answer)) {
            res += 10;
            styleClass = "correct";
        } else {
            styleClass = "wrong";
        }
        setImage(styleClass);
        showAnswer(styleClass);
        scene.getRoot().setMouseTransparent(true);

        PauseTransition delay = new PauseTransition(Duration.millis(2000));
        delay.setOnFinished(e -> {
            scene.getRoot().setMouseTransparent(false);
            resetOptions(styleClass);
            if (idx < 9) {
                idx++;
                showOption(idxList[idx]);
            } else if (idx == 9) {
                showResult();
            }
        });
        delay.play();
    }

    @Override
    public void decor() {
        idxList = GameQuestion.randomSeed();
        idx = 0;
        this.timer();
        this.showOption(idxList[idx]);
        this.gamePlay();
    }
}