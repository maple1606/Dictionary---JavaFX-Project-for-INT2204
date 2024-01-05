package main.UI.Components.DynamicField;

import java.util.ArrayList;

import javafx.geometry.Insets;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import main.Server.Database.DictionaryManager.WordManager.ExtensionManager.ContributionManager.WordEdit;
import main.Server.Database.NewsfeedManager.FeedBox;

public class FeedField extends DynamicField {
    private Text target = new Text();
    private Text partOfSpeech = new Text();
    private Text definition = new Text();
    private Text example = new Text();
    private VBox explainBox = new VBox();
    private FeedBox feedBox = new FeedBox("", "", "");
    private ArrayList<FeedField> feedFields = new ArrayList<>();

    public FeedField(FeedBox feedBox, ArrayList<FeedField> feedFields, VBox container) {
        this.feedFields = feedFields;
        this.container = container;
        this.feedBox =  feedBox;
        feedFields.add(this);

        super.initialize();

        close.setPrefWidth(25.97);
        close.setPrefHeight(23.86);

        rectangle.setWidth(750.79);
        rectangle.setHeight(412);
        rectangle.setLayoutX(12.21);
        rectangle.setLayoutY(14);

        target.setText(this.feedBox.getWordTarget());
        target.setLayoutX(70);
        target.setLayoutY(65);
        target.setFont(Font.font("Montserrat SemiBold", FontWeight.NORMAL, 24));

        Font semiboldFont = Font.font("Montserrat SemiBold", FontWeight.NORMAL, 16.75);
        Font italicFont = Font.font("Montserrat Italic", FontWeight.NORMAL, 16.75);
        Font regularFont = Font.font(Font.getDefault().getFamily(), FontWeight.NORMAL, 14.5);
        
        partOfSpeech = new Text(this.feedBox.getPartOfSpeech());
        String[] defStr = this.feedBox.getDefinition().split("\n");
        definition = new Text(defStr[0]);
        example = new Text(defStr[1]);

        partOfSpeech.setFont(italicFont);
        definition.setFont(semiboldFont);
        example.setFont(regularFont);

        explainBox.setPadding(new Insets(20, 20, 20, 20));
        explainBox.setSpacing(15);
        explainBox.getChildren().add(partOfSpeech);
        explainBox.getChildren().add(definition);
        explainBox.getChildren().add(example);

        explainBox.setLayoutX(42);
        explainBox.setLayoutY(97);
        explainBox.setPrefWidth(692);
        explainBox.setPrefHeight(295.08);
        explainBox.setStyle(explain_field);

        containerPane.getChildren().add(rectangle);
        containerPane.getChildren().add(target);
        containerPane.getChildren().add(explainBox);
        containerPane.getChildren().add(close);
    }

    public AnchorPane getContainerPane() {
        return this.containerPane;
    }

    @Override
    public String getExplain() {
        return definition.getText()
                + "\n" + example.getText();
    }

    @Override
    protected void removeBox(MouseEvent event) {
        if (feedFields.size() > 1) {
            System.out.println(getExplain());
            feedFields.remove(this);
            container.getChildren().remove(this.getContainerPane());
            WordEdit.removeDefinition(target.getText(), 
                                      partOfSpeech.getText(),
                                      getExplain());
        }
    }
}
