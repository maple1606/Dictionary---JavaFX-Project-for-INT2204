package main.UI.Components.DynamicField;

import java.util.ArrayList;

import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import main.Server.Database.DictionaryManager.WordManager.ExtensionManager.ContributionManager.WordEdit;
import main.UI.Controller.Screens.Dictionary.DictionaryControl;

public class EditField extends DynamicField {
    private TextField partOfSpeechField = new TextField();
    private TextField definitionField = new TextField();
    private TextField exampleField = new TextField();
    private ArrayList<EditField> editFields = new ArrayList<>();
    private String orgPartOfSpeech;
    private String orgDefinition;
    private String orgExample;

    public EditField(ArrayList<EditField> editFields, VBox container) {
        this.editFields = editFields;
        this.container = container;

        super.initialize();

        close.setPrefWidth(36);
        close.setPrefHeight(33.5);

        rectangle.setWidth(918);
        rectangle.setHeight(402.37);
        rectangle.setLayoutX(15);
        rectangle.setLayoutY(13.34);

        partOfSpeechField.setLayoutX(35.65);
        partOfSpeechField.setLayoutY(41.13);
        partOfSpeechField.setPrefWidth(877.35);
        partOfSpeechField.setPrefHeight(42.35);
        partOfSpeechField.setPromptText("Part of speech");
        partOfSpeechField.setStyle(explain_field);

        definitionField.setLayoutX(35.65);
        definitionField.setLayoutY(120.71);
        definitionField.setPrefWidth(877.35);
        definitionField.setPrefHeight(120.62);
        definitionField.setPromptText("Type your definition here...");
        definitionField.setStyle(explain_field);

        exampleField.setLayoutX(35.65);
        exampleField.setLayoutY(265.1);
        exampleField.setPrefWidth(877.35);
        exampleField.setPrefHeight(118.37);
        exampleField.setPromptText("Type an example of how it's used in a sentece...");
        exampleField.setStyle(explain_field);

        containerPane.getChildren().add(rectangle);
        containerPane.getChildren().add(partOfSpeechField);
        containerPane.getChildren().add(definitionField);
        containerPane.getChildren().add(exampleField);
        containerPane.getChildren().add(close);
    }

    public boolean isEmpty() {
        return partOfSpeechField.getText().isEmpty() ||
                definitionField.getText().isEmpty() ||
                exampleField.getText().isEmpty();
    }

    public void setEditField(String partOfSpeech, String definition, String example) {
        partOfSpeechField.setText(partOfSpeech);
        definitionField.setText(definition);
        exampleField.setText(example);

        orgPartOfSpeech = partOfSpeech;
        orgDefinition = definition;
        orgExample = example;
    }

    public AnchorPane getContainerPane() {
        return this.containerPane;
    }

    public String getPartOfSpeech() {
        return partOfSpeechField.getText();
    }

    public String getOrgPartOfSpeech() {
        return orgPartOfSpeech;
    }

    @Override
    public String getExplain() {
        return "• " + definitionField.getText()
                + "\n" + "    ◦ " + exampleField.getText();
    }

    public String getOrgExplain() {
        return "• " + orgDefinition
                + "\n" + "    ◦ " + orgExample;
    }

    @Override
    protected void removeBox(MouseEvent event) {
        if (editFields.size() > 1
                && WordEdit.getNumberOfSavedDefinitions(DictionaryControl.getTarget()) > 1) {
            editFields.remove(this);
            container.getChildren().remove(this.getContainerPane());
            WordEdit.removeDefinition(DictionaryControl.getTarget(),
                    partOfSpeechField.getText(),
                    getExplain());
        }
    }
}