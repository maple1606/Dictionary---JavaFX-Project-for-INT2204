package main.UI.Controller;

import java.io.IOException;
import java.net.URL;
import java.util.ResourceBundle;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;
import main.Server.Database.UserManaer.User;
import main.UI.Controller.Alerts.AlertControl;
import main.UI.Controller.Screens.ScreenControl;

public class FXMLControl implements Initializable {
    protected static Stage primaryStage;
    protected static Stage alertStage;
    protected static User usr;
    protected static ScreenControl screen;
    protected static AlertControl alert;
    protected static Scene scene;

    public static void setPrimaryStage(Stage _primaryStage) {
        primaryStage = _primaryStage;
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }

    public static void setUser(User _usr) {
        usr = _usr;
    }

    public static User getUser() {
        return usr;
    }

    @Override
    public void initialize(URL location, ResourceBundle resources) {

    }

    protected void loadScreenFXML(String screenFXML) {
        String path = "resources/fxml/Screens/" + screenFXML + ".fxml";
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getClassLoader().getResource(path));

            Parent root = loader.load();
            
            scene = new Scene(root);
            primaryStage.setScene(scene);
            primaryStage.setResizable(false);
            primaryStage.show();

            screen = loader.getController();
            screen.decor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    protected void loadAlertFXML(String alertFXML) {
        String path = "resources/fxml/Alerts/" + alertFXML + ".fxml";
        try {
            FXMLLoader loader = new FXMLLoader(
                    getClass().getClassLoader().getResource(path));

            Parent root = loader.load();

            alertStage = new Stage();
            alertStage.initOwner(ScreenControl.getPrimaryStage());
            alertStage.initModality(Modality.APPLICATION_MODAL);
            alertStage.setScene(new Scene(root));
            alertStage.setResizable(false);
            alertStage.show();

            alert = loader.getController();
            alert.decor();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void decor() {
        // do nothing
    }
}