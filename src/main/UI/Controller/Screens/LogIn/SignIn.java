package main.UI.Controller.Screens.LogIn;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.control.PasswordField;
import main.Server.Database.DatabaseManager;
import main.Server.Database.UserManaer.User;
import main.UI.Controller.FXMLControl;
import main.UI.Controller.Screens.ScreenControl;

public class SignIn extends ScreenControl {
    @FXML
    protected TextField usrname;
    @FXML
    protected PasswordField pwd;
    
    @FXML
    protected void up() {
        super.loadScreenFXML("LogIn/signUp");
    }

    @FXML
    private void signIn() {
        String _usrname = usrname.getText();
        String _pwd = pwd.getText();

        User usr = new User();

        String rs = usr.signIn(_usrname, _pwd);

        if (rs.equals("Signed in successfully!")) {
            FXMLControl.setUser(usr);
            DatabaseManager.setUser(usr);
            super.loadScreenFXML("Dashboard/dashboard");
        }
    }
}
