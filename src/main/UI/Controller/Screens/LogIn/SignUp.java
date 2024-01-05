package main.UI.Controller.Screens.LogIn;

import javafx.fxml.FXML;
import main.Server.Database.UserManaer.User;

public class SignUp extends SignIn {
    @FXML
    private void signUp() {
        String _usrname = usrname.getText();
        String _pwd = pwd.getText();

        User usr = new User();

        String rs = usr.signUp(_usrname, _pwd);

        System.out.println(rs);

        if (rs.equals("Signed up successffully!")) {
            super.navigate();
        }
    }
}
