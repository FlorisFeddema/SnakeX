package SnakeX.Client.UI;

import SnakeX.Client.Logic.IsClient;
import SnakeX.Model.enums.MoveDirection;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameController extends Controller {

    public void setClient(IsClient client){
        super.setClient(client);
        Client.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.DOWN) {
                client.move(MoveDirection.Down);
            }
        });
        Client.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.UP) {
                client.move(MoveDirection.Up);
            }
        });
        Client.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.LEFT) {
                client.move(MoveDirection.Left);
            }
        });
        Client.stage.getScene().addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.RIGHT) {
                client.move(MoveDirection.Right);
            }
        });
    }
}
