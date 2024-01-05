package main.UI.Controller.Screens.Dictionary;

import java.util.ArrayList;
import java.util.Map;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import main.Server.Database.DictionaryManager.WordManager.Word;
import main.Server.Database.DictionaryManager.WordManager.ExtensionManager.ContributionManager.WordContribute;
import main.Server.Database.DictionaryManager.WordManager.ExtensionManager.ContributionManager.WordEdit;
import main.UI.Components.DynamicField.EditField;

public class EditControl extends AddControl {
    @FXML
    private Button backButton;

    private void backToDictionary(MouseEvent event) {
        super.dictionaryStarted(DictionaryControl.getTarget());
    }

    @Override
    public void decor() {
        wordField.setText(DictionaryControl.getTarget());
        editFields.clear();
        Word word = new Word();
        word.setWordTarget(DictionaryControl.getTarget());
        backButton.setOnMouseClicked(this::backToDictionary);
        WordContribute.getUserEdit(word);
        Map<String, ArrayList<String>> wordExplain = word.getWordExplain();
        for (String partOfSpeech : word.getWordPartOfSpeech()) {
            String definition = "";
            String example = "";
            for (String explain : wordExplain.get(partOfSpeech)) {
                if (explain.charAt(0) == '•') {
                    definition = explain.substring(2);
                } else {
                    example = explain.substring(6);
                    EditField editField = new EditField(editFields, vBox);
                    editField.setEditField(partOfSpeech, definition, example);
                    editFields.add(editField);
                }
            }
        }
        for (EditField editField : editFields) {
            vBox.getChildren().add(editField.getContainerPane());
        }
    }

    @FXML
    private void saveWord() {
        target = wordField.getText();

        if (editFields.isEmpty()) {
            return;
        }

        for (EditField e : editFields) {
            if (!e.isEmpty()) {
                String oldPartOfSpeech = e.getOrgPartOfSpeech();
                String oldDefinition = e.getOrgExplain();
                String newPartOfSpeech = e.getPartOfSpeech();
                String newDefinition = e.getExplain();

                WordEdit.saveDefinition(target,
                        oldPartOfSpeech, newPartOfSpeech,
                        oldDefinition, newDefinition);

                String[] explain = newDefinition.split("\n");
                e.setEditField(newPartOfSpeech,
                        explain[0].substring(2),
                        explain[1].substring(6));
            }
        }
    }
}