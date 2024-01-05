package main.UI.Controller.Screens.Dictionary;

import java.util.ArrayList;

import javafx.util.Duration;
import javafx.util.Pair;
import javafx.animation.PauseTransition;
import javafx.fxml.FXML;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.TextField;
import javafx.scene.layout.Border;
import javafx.scene.layout.BorderStroke;
import javafx.scene.layout.BorderStrokeStyle;
import javafx.scene.layout.CornerRadii;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import main.Server.Database.DictionaryManager.WordManager.ExtensionManager.ContributionManager.WordAdd;
import main.UI.Components.DynamicField.EditField;
import main.UI.Controller.Alerts.DeleteAlert;
import main.UI.Controller.Screens.ScreenControl;

public class AddControl extends ScreenControl {
    protected ArrayList<EditField> editFields = new ArrayList<>();
    protected String target;
    protected ArrayList<Pair<String, String>> definition = new ArrayList<>();
    @FXML
    protected VBox vBox;
    @FXML
    protected TextField wordField;
    @FXML 
    protected ScrollPane scrollPane;

    @FXML
    private void saveWord() {
        definition.clear();
        target = wordField.getText();
        for (EditField e : editFields) {
            String partOfSpeech = e.getPartOfSpeech();
            definition.add(new Pair<>(partOfSpeech, e.getExplain()));
        }
        WordAdd.add(target, definition);
        DictionaryControl.setTarget(target);
        super.loadScreenFXML("Dictionary/editWord");
    }

    public ArrayList<EditField> getEditFields() {
        return editFields;
    }

    @FXML
    protected void addDefinition() {
        EditField e = new EditField(editFields, vBox);
        editFields.add(e);
        vBox.getChildren().add(0, e.getContainerPane());

        BorderStroke borderStroke = new BorderStroke(
                Color.valueOf("#FF4D56"),
                BorderStrokeStyle.SOLID,
                new CornerRadii(30), 
                new javafx.scene.layout.BorderWidths(5));
        vBox.setBorder(new Border(borderStroke));

        PauseTransition pause = new PauseTransition(Duration.seconds(0.3));
        pause.setOnFinished(event -> vBox.setBorder(null));
        pause.play();
        scrollPane.setVvalue(0);
    }

    @FXML
    protected void deleteAlert() {
        DeleteAlert.setTarget(target);
        super.loadAlertFXML("delete");
    }

    @Override
    public void decor() {
        editFields.clear();
        editFields.add(new EditField(editFields, vBox));
        for (EditField e : editFields) {
            vBox.getChildren().add(e.getContainerPane());
        }
    }
}
