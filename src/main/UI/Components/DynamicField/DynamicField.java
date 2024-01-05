package main.UI.Components.DynamicField;

import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import main.UI.Components.ComponentManager;

public abstract class DynamicField implements ComponentManager {
    protected Button close = new Button();
    protected Rectangle rectangle = new Rectangle();
    protected AnchorPane containerPane = new AnchorPane();
    protected VBox container = new VBox();
    protected String fixed_box = "-fx-background-radius: 30px;\n" + //
            "    -fx-fill: #fff;\n" + //
            "    -fx-arc-height: 30px;\n" + //
            "    -fx-arc-width: 30px;";

    protected String explain_field = " -fx-border-radius: 24px;\n" + //
            "    -fx-border-color: #f0f0f0;\n" + //
            "    -fx-border-width: 12px; \n" + //
            "    -fx-font-size: 16px;\n" + //
            "    -fx-control-inner-background: #f0f0f0;\n" + //
            "    -fx-background-color: #f0f0f0; \n" + //
            "    -fx-fill: #f0f0f0;\n" + //
            "    -fx-alignment: top-left;\n" + //
            "    -fx-text-fill: #9a9a9a;\n" + //
            "    -fx-arc-height: 24px;\n" + //
            "    -fx-arc-width: 24px;\n" + //
            "    -fx-background-radius: 24px;";

    protected String transparent = "-fx-background-color: transparent;";

    @Override
    public void initialize() {
        ImageView buttonImageView = new ImageView(new Image("resources/icons/x-circle.png"));
        close.setGraphic(buttonImageView);
        close.setAlignment(Pos.CENTER);
        close.setLayoutX(0);
        close.setLayoutY(0);
        close.setStyle(transparent);
        close.setOnMouseClicked(this::removeBox);

        rectangle.setStyle(fixed_box);
    }

    protected AnchorPane getContainerPane() {
        return containerPane;
    }

    public abstract String getExplain();
    
    protected abstract void removeBox(MouseEvent event);     
}
