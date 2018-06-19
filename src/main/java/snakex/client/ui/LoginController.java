package snakex.client.ui;

import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends Controller {

    public TextField tbUsername;
    public TextField tbPassword;

    @FXML
    private void btnLoginPress(){
        String name = tbUsername.getText();
        String password = tbPassword.getText();
        loginPlayer(name, password);
    }

    @FXML
    private void btnRegisterPress(){
        try {
            changeScene("Register", client);
        } catch (IOException e) {
            //ignore
        }
    }

    @FXML
    private void btnExitPress(){
        System.exit(0);
    }

    private void loginPlayer(String name, String password){
        try {
            if (client.loginPlayer(name, password) > 0){
                changeScene("Main", client);

            } else {
                showAlert("Credentials are not correct", "Credentials error");
            }
        } catch (IOException|InterruptedException|NullPointerException e) {
            showAlert("Problem with the connection", "Connection error");
        }

    }

}
