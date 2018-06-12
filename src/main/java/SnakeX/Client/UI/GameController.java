package SnakeX.Client.UI;

import SnakeX.Client.Logic.IsClient;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;

public class GameController extends Controller {

    public void setClient(IsClient client){
        super.setClient(client);
        scene.addEventHandler(KeyEvent.KEY_PRESSED, (key) -> {
            if(key.getCode()==KeyCode.ENTER) {
            }
        });
    }
}
