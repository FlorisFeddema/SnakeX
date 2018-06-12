package SnakeX.Client.UI;

import SnakeX.Client.Logic.IsClient;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.Alert;

import java.io.IOException;



public abstract class Controller{

    protected IsClient client;
    protected Scene scene;

    public void changeScene(String fxml, IsClient client) throws IOException {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/" + fxml + ".fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        scene = new Scene(root);
        Client.stage.setTitle("SnakeX");
        Client.stage.setScene(scene);
        Client.stage.centerOnScreen();
        Client.stage.show();
        controller.setClient(client);
    }

    public void setClient(IsClient client) {
        this.client = client;
    }

    protected void showAlert(String message, String header){
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle(header);
        alert.setContentText(message);
        alert.show();
    }
}
