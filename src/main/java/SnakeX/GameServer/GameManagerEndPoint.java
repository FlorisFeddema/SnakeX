package SnakeX.GameServer;

import SnakeX.Model.Shared.Point;
import SnakeX.Model.Shared.Snake;
import SnakeX.Shared.ConsoleColors;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

import static SnakeX.GameServer.GameServer.gameModel;
import static SnakeX.Shared.Static.keyInJson;

@ClientEndpoint
public class GameManagerEndPoint implements IsGameManagerEndPoint {
    Session server;

    public GameManagerEndPoint(String url) {
        URI uri = URI.create("ws://localhost:9900/snake/manager/");
        try {

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            //connects to server
            server = container.connectToServer(this, uri);
            connectServer(url);
        } catch (Throwable t) {

        }
    }


    private void connectServer(String url){
        JsonObject json = new JsonObject();
        json.addProperty("gameserver", true);
        json.addProperty("url", url);
        try {
            server.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            //ignore
        }
    }

    @OnError
    public void onWebSocketError(Throwable cause) {
        System.out.println(ConsoleColors.PURPLE + "Game: We got an error");
        cause.printStackTrace(System.err);
    }


    @OnMessage
    public void onWebSocketText(String message, Session session) {
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        if (keyInJson(json, "spawn")) {
            setPlayer(json);
        }
    }

    private void setPlayer(JsonObject json){
        int id = json.get("id").getAsInt();
        int x = json.get("x").getAsInt();
        int y = json.get("y").getAsInt();
        int rating = json.get("rating").getAsInt();
        int length = json.get("length").getAsInt();
        Point point = new Point(x, y);
        Snake snake = new Snake(5, point, id);
        gameModel.setPlayer(snake);

    }



}
