package main.UI.Controller.Screens.Game;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.event.ActionEvent;
import main.Server.Database.GameManager.ChoiceQuestion;
import javafx.scene.image.Image;

public class ChoiceGame extends GameControl {
    private ChoiceQuestion choice = new ChoiceQuestion();
    private String key;

    @FXML
    private Button quesBox;
    @FXML
    private Button ansBoxA;
    @FXML
    private Button ansBoxB;
    @FXML
    private Button ansBoxC;
    @FXML
    private Button ansBoxD;

    private Button selectedButton = new Button();

    @Override
    protected boolean isAnswerCorrect(String answer) {
        return answer.charAt(0) == key.charAt(0);
    }

    @Override
    protected void showAnswer(String styleClass) {
        changeStyleClass(selectedButton, "opt-box", styleClass);
    }

    @Override
    protected void resetOptions(String styleClass) {
        gameMascot.setImage(new Image("resources/image/catGlasses.png"));
        changeStyleClass(selectedButton, styleClass, "opt-box");
    }

    @Override
    @FXML
    protected void selectOption(ActionEvent event) {
        selectedButton = (Button) event.getSource();
        processAnswer(selectedButton.getText());
    }

    @Override
    @FXML
    protected void showOption(int id) {
        String str = "Question " + String.valueOf(idx + 1) + "/10";
        index.setText(str);

        quesBox.setText(choice.getQuestion(id));

        String A = "A. " + choice.getOptions(id, 0);
        ansBoxA.setText(A);
        String B = "B. " + choice.getOptions(id, 1);
        ansBoxB.setText(B);
        String C = "C. " + choice.getOptions(id, 2);
        ansBoxC.setText(C);
        String D = "D. " + choice.getOptions(id, 3);
        ansBoxD.setText(D);
        this.key = choice.getCorrect(id);
    }

    @Override
    protected void gamePlay() {
        mascot = "cat";
    }
}