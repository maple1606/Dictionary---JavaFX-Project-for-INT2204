package main.UI.Controller.Alerts;

import javafx.fxml.FXML;
import main.UI.Controller.FXMLControl;

public abstract class AlertControl extends FXMLControl {
    @FXML
    protected void cancel() {
        alertStage.close();
    }
}
