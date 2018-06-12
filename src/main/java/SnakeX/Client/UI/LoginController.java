package SnakeX.Client.UI;

import SnakeX.Shared.ConsoleColors;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;

import java.io.IOException;

public class LoginController extends Controller {

    public TextField tbUsername;
    public TextField tbPassword;

    public LoginController(){
    }

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
            e.printStackTrace();
        }
    }

    @FXML
    private void btnExitPress(){
        System.exit(0);
    }

    private void loginPlayer(String name, String password){
        try {
            if (client.loginPlayer(name, password) > 0){
                System.out.println(ConsoleColors.GREEN + "Client: Logged In");
                changeScene("Main", client);

            } else {
                showAlert("Credentials are not correct", "Credentials error");
            }
        } catch (IOException e) {
            showAlert("Problem with the connection", "Connection error");
        } catch (InterruptedException e) {
            showAlert("Problem with the connection", "Connection error");
        } catch (NullPointerException e) {
            showAlert("Problem with the connection", "Connection error");
        }

    }

}
