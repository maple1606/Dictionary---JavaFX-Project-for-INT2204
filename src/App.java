import javafx.application.Application;
import javafx.application.Platform;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import main.Constants;
import main.Server.Database.DatabaseManager;
import main.UI.Controller.FXMLControl;

public class App extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(
                getClass().getClassLoader().getResource("resources/fxml/Screens/LogIn/signIn.fxml"));
        Parent root = loader.load();

        Scene scene = new Scene(root, Constants.width, Constants.heigth);

        primaryStage.setTitle("OOP Dictionary");
        primaryStage.setScene(scene);
        primaryStage.setResizable(false);
        primaryStage.setOnCloseRequest(
                arg0 -> {
                    Platform.exit();
                    System.exit(0);
                });
        primaryStage.show();

        FXMLControl.setPrimaryStage(primaryStage);
        DatabaseManager.initialize();
    }
}