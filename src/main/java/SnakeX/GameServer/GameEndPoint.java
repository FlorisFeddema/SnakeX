package SnakeX.GameServer;

import SnakeX.Model.enums.MoveDirection;
import SnakeX.Shared.ConsoleColors;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import jdk.nashorn.internal.scripts.JO;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;

import static SnakeX.Shared.Static.keyInJson;
import static SnakeX.GameServer.GameServer.gameModel;


@ServerEndpoint(value = "/snake/game/")
public class GameEndPoint {

    @OnOpen
    public void connect(Session session){
        //ignore
    }

    @OnClose
    public void close(Session session){
        //ignore
    }

    @OnError
    public void onError(Throwable cause, Session session) {
        System.out.println(ConsoleColors.PURPLE + "Game: A client got an error");
        cause.printStackTrace(System.err);
    }

    @OnMessage
    public void onMessageReceived(String message, Session session) {
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        if (keyInJson(json, "connect")) {
            connectPlayer(json, session);
        } else if (keyInJson(json, "direction")){
            updateDirection(json, session);
        }
    }

    private void connectPlayer(JsonObject json, Session session) {
        int id = json.get("id").getAsInt();
        gameModel.connectPlayer(id, session);
    }

    private void updateDirection(JsonObject json, Session session) {
        MoveDirection direction = MoveDirection.valueOf(json.get("direction").getAsString());
        gameModel.updateDirection(direction, session);
    }

}
