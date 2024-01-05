package main.UI.Controller.Alerts;

import javafx.fxml.FXML;
import main.Server.Database.DictionaryManager.WordManager.ExtensionManager.ContributionManager.WordDelete;

public class DeleteAlert extends AlertControl {
    private static String target;

    public DeleteAlert() {

    }

    public static void setTarget(String _target) {
        target = _target;
    }

    @FXML 
    private void deleteWord() {
        WordDelete.delete(target);
        alertStage.close();
        super.loadScreenFXML("Dashboard/dashboard");
    }
}
