package main.UI.Controller.Screens.Translation;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.Node;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
import javafx.scene.text.Text;
import javafx.scene.text.TextFlow;
import javafx.stage.FileChooser;
import main.Server.API.TranslateAPI;
import main.UI.Controller.Screens.ScreenControl;
import main.Server.API.TTSAPI;
 
public class TranslationControl extends ScreenControl {
    @FXML
    private Text langFrom;
    @FXML
    private Text langTo;
    @FXML
    private TextArea inp;
    @FXML
    private TextFlow out;
    @FXML
    private Button switchButton;

    public void decor() {
        langFrom.setText("English");
        langTo.setText("Vietnamese");
        this.addTranslationListener();
    }

    private void addTranslationListener() {
        inp.addEventHandler(KeyEvent.KEY_RELEASED, event -> {
            if (event.getCode() == KeyCode.ENTER) {
                this.translateText();
            }
        });
    }

    private void translateText() {
        out.getChildren().clear();
        String inputText = inp.getText();
        String translatedText;
        if (langTo.getText().equals("Vietnamese")) {
            translatedText = TranslateAPI.toVi(inputText);
        } else {
            translatedText = TranslateAPI.toEn(inputText);
        }
        Text text = new Text(translatedText);
        out.getChildren().add(text);
    }

    @FXML
    private void switchLang() {
        String temp = langFrom.getText();
        langFrom.setText(langTo.getText());
        langTo.setText(temp);
        out.getChildren().clear();
        inp.setText("");
    }

    @FXML
    private void listen() {
        if (langTo.getText().equals("Vietnamese")) {
            TTSAPI.playVi(this.getOutput());
        } else {
            TTSAPI.playEn(this.getOutput());
        }
    }

    private String getOutput() {
        String content = "";
        for (Node child : out.getChildren()) {
            if (child instanceof Text) {
                Text textNode = (Text) child;
                String text = textNode.getText();
                content += text;
            }
        }
        return content;
    }

    @FXML
    private void uploadFile() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.getExtensionFilters().add(new FileChooser.ExtensionFilter("text", "*.txt"));
        File file = fileChooser.showOpenDialog(null);

        if (file != null) {
            String content = "";
            try (BufferedReader reader = new BufferedReader(new FileReader(file))) {
                String line;
                while ((line = reader.readLine()) != null) {
                    content += line;
                    content += "\n";
                }
                inp.setText(content);
                this.translateText();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
