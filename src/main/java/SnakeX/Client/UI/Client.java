package SnakeX.Client.UI;

import SnakeX.Client.Logic.ClientGame;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;


public class Client extends Application {

    public static Stage stage;

    public static void main(String[] args) {
        Application.launch();
    }



    @Override
    public void start(Stage primaryStage) throws Exception {
        FXMLLoader loader = new FXMLLoader(getClass().getClassLoader().getResource("views/Login.fxml"));
        Parent root = loader.load();
        Controller controller = loader.getController();
        controller.setClient(new ClientGame());
        Scene scene = new Scene(root);
        stage = primaryStage;
        stage.setTitle("SnakeX");
        stage.setScene(scene);
        stage.show();
    }
}
