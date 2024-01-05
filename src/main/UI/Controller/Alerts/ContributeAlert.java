package main.UI.Controller.Alerts;

import javafx.fxml.FXML;

public class ContributeAlert extends AlertControl {
    private static String contributeType;

    public static void setContributeType(String _contributeType) {
        contributeType = _contributeType;
    }

    @FXML
    private void contributeWord() {
        alertStage.close();
        super.loadScreenFXML("Dictionary/" + contributeType);
    }
}
