package snakex.gameserver;

import snakex.model.shared.Point;
import snakex.model.shared.Snake;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;

import static snakex.gameserver.GameServer.gameModel;
import static snakex.shared.Static.keyInJson;

@ClientEndpoint
public class GameManagerEndPoint implements IsGameManagerEndPoint {
    Session server;

    public GameManagerEndPoint(String url) {
        URI uri = URI.create("ws://217.105.43.173:9900/snake/manager/");
        try {

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            //connects to server
            server = container.connectToServer(this, uri);
            connectServer(url);
        } catch (Exception t) {
            //ignore
        }
    }

    public void endGame(int id1, int id2){
        JsonObject json = new JsonObject();
        json.addProperty("end", true);
        json.addProperty("id1", id1);
        json.addProperty("id2", id2);
        try {
            server.getBasicRemote().sendText(json.toString());
        } catch (IOException e) {
            //ignore
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
        //ignore
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
        Snake snake = new Snake(length, point, id, rating);
        gameModel.setPlayer(snake);

    }



}
