package main.UI.Controller.Screens.Game;

import java.util.ArrayList;
import java.util.Arrays;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.StrokeType;
import main.Server.Database.GameManager.OrderQuestion;

public class OrderGame extends GameControl {
    private OrderQuestion order = new OrderQuestion();

    private double[] coorX = new double[6];
    private double[] coorY = new double[6];
    private double[] orgX = new double[6];
    private double[] orgY = new double[6];
    private double width;
    private double height;

    ArrayList<Button> buttonLs = new ArrayList<>();
    ArrayList<Button> optionLs = new ArrayList<>();

    @Override
    protected void gamePlay() {
        mascot = "bear";
        optionLs = new ArrayList<>(Arrays.asList(optBox1, optBox2, optBox3,
                optBox4, optBox5, optBox6));

        for (int i = 0; i < optionLs.size(); i++) {
            Button bo = optionLs.get(i);
            orgX[i] = bo.getLayoutX();
            orgY[i] = bo.getLayoutY();
        }

        for (int i = 0; i < isButtonSelected.length; i++) {
            isButtonSelected[i] = false;
        }
    }

    @FXML
    private Button optBox1;
    @FXML
    private Button optBox2;
    @FXML
    private Button optBox3;
    @FXML
    private Button optBox4;
    @FXML
    private Button optBox5;
    @FXML
    private Button optBox6;
    @FXML
    private HBox boxContainer;

    private boolean[] isButtonSelected = new boolean[6];

    private int key;

    @Override
    @FXML
    protected void showOption(int id) {
        showBox(id);
        optBox1.setText(order.getOptions(id, 0));
        optBox2.setText(order.getOptions(id, 1));
        optBox3.setText(order.getOptions(id, 2));
        optBox4.setText(order.getOptions(id, 3));
        optBox5.setText(order.getOptions(id, 4));
        optBox6.setText(order.getOptions(id, 5));
    }

    @Override
    @FXML
    protected void selectOption(ActionEvent event) {
        Button selectedButton = (Button) event.getSource();
        int cnt = order.countWords();
        if (cnt <= key) {
            int selectedIndex = Arrays.asList(optBox1, optBox2, optBox3,
                    optBox4, optBox5, optBox6).indexOf(selectedButton);
            if (selectedIndex >= 0 && selectedIndex < 6) {
                if (isButtonSelected[selectedIndex] == false && cnt < key) {
                    order.addString(selectedButton.getText());
                    buttonLs.add(selectedButton);

                    isButtonSelected[selectedIndex] = true;

                    selectedButton.setLayoutX(coorX[cnt]);
                    selectedButton.setLayoutY(coorY[cnt]);
                    selectedButton.setPrefWidth(width + 8);
                    selectedButton.setPrefHeight(height + 8);
                } else if (isButtonSelected[selectedIndex] == true) {
                    // undo an option.
                    if (selectedButton.getText().equals(order.getLastWord())) {
                        order.substractString();
                        buttonLs.remove(selectedButton);
                        isButtonSelected[selectedIndex] = false;
                        selectedButton.setLayoutX(orgX[selectedIndex]);
                        selectedButton.setLayoutY(orgY[selectedIndex]);
                        selectedButton.setPrefWidth(124);
                        selectedButton.setPrefHeight(166);
                    } else {
                        // do nothing.
                    }
                }
            }
        }
    }

    @Override
    protected boolean isAnswerCorrect(String selectedAns) {
        return order.correct(idxList[idx]);
    }

    @Override
    protected void showAnswer(String styleClass) {
        for (Button button : buttonLs) {
            if (button != null) {
                changeStyleClass(button, "opt-box", styleClass);
            }
        }
    }

    @Override
    protected void resetOptions(String stylesClass) {
        gameMascot.setImage(new Image("resources/image/bearGlasses.png"));
        order.setStr("");

        for (int i = 0; i < optionLs.size(); i++) {
            Button bo = optionLs.get(i);
            bo.setLayoutX(88 + (124 + 28) * i);
            bo.setLayoutY(341);
        }

        for (Button button : buttonLs) {
            if (button != null) {
                button.setPrefWidth(124);
                button.setPrefHeight(166);
                changeStyleClass(button, stylesClass, "opt-box");

            }
        }

        buttonLs.clear();

        for (int i = 0; i < isButtonSelected.length; i++) {
            isButtonSelected[i] = false;
        }
    }

    @FXML
    private void submit() {
        processAnswer(order.getStr());
    }

    private void showBox(int id) {
        String str = "Question " + String.valueOf(idx + 1) + "/10";
        index.setText(str);
        key = order.countWords(id);
        boxContainer.getChildren().clear();
        boxContainer.setMaxWidth(732);
        boxContainer.setMaxHeight(166);

        double spacing = 34;
        if (key == 5)
            spacing = 28;
        if (key == 6)
            spacing = 23;

        width = (boxContainer.getMaxWidth() - spacing * (key - 1)) / key - 8;
        height = 166 - 8;

        boxContainer.setSpacing(spacing);

        for (int i = 0; i < key; i++) {
            Rectangle rectangle = new Rectangle(width, height);
            rectangle.setStroke(Color.BLACK);
            rectangle.setStrokeWidth(1);
            rectangle.setStrokeType(StrokeType.CENTERED);
            rectangle.getStrokeDashArray().addAll(10d, 10d);
            rectangle.setFill(Color.TRANSPARENT);

            rectangle.setArcWidth(15);
            rectangle.setArcHeight(15);
            boxContainer.getChildren().add(rectangle);

            coorX[i] = boxContainer.getLayoutX() + (width + spacing) * i - 1.5;
            coorY[i] = 126;
        }
    }
}