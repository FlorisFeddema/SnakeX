package SnakeX.Client.UI;

import SnakeX.Shared.ConsoleColors;
import javafx.fxml.FXML;
import javafx.scene.control.*;

import java.io.IOException;

public class RegisterController extends Controller{

    public PasswordField tbPassword;
    public TextField tbUsername;

    @FXML
    public void btnRegisterPress() {
        String name = tbUsername.getText();
        registerPlayer(name, tbPassword.getText());
    }

    @FXML
    public void btnExitPress() {
        try {
            changeScene("Login", client);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void registerPlayer(String username, String password){
        if (checkPassword(password) && checkUsername(username)){
            showAlert("Password or username does not meet criteria", "Credentials error");
            return;
        }
        try {
            if (client.registerPlayer(username, password)){
                System.out.println(ConsoleColors.GREEN + "Client: Registerd");
                changeScene("Login", client);
            } else {
                showAlert("Username is already in use", "Username error");
            }
        } catch (IOException | NullPointerException | InterruptedException e) {
            showAlert("Problem with the connection", "Connection error");
        }
    }

    private boolean checkPassword(String password){
        return password.length() < 3;
    }

    private boolean checkUsername(String username){
        return username.length() < 3;
    }
}
