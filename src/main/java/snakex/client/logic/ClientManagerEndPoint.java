package snakex.client.logic;

import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import snakex.model.shared.Point;

import javax.websocket.*;
import java.io.IOException;
import java.net.URI;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.TimeUnit;

import static snakex.shared.Static.keyInJson;

@ClientEndpoint
public class ClientManagerEndPoint implements IsClientManagerEndPoint {

    private IsControllerClient client;
    private Session manager;

    private SynchronousQueue<Integer> playerId;
    private SynchronousQueue<Boolean> register;
    private SynchronousQueue<JsonObject> stats;

    public ClientManagerEndPoint(IsControllerClient client) {
        playerId = new SynchronousQueue();
        register = new SynchronousQueue();
        stats = new SynchronousQueue();

        this.client = client;

        URI uri = URI.create("ws://217.105.43.173:9900/snake/manager/");
        try {

            WebSocketContainer container = ContainerProvider.getWebSocketContainer();
            //connects to manager
            manager = container.connectToServer(this, uri);
        } catch (Exception t) {
            //ignore
        }
    }


    @OnError
    public void onWebSocketError(Throwable cause) {
        //ignore
    }


    @OnMessage
    public void onWebSocketText(String message) {
        JsonObject json = new JsonParser().parse(message).getAsJsonObject();
        if (keyInJson(json, "login")) {
            playerId.offer(json.get("id").getAsInt());
        } else if (keyInJson(json, "register")){
            register.offer(json.get("register").getAsBoolean());
        } else if (keyInJson(json, "stats")){
            stats.offer(json);
        } else if (keyInJson(json, "message")){
            receiveMessageOther(json);
        } else if (keyInJson(json, "gameserver")){
            joinGame(json);
        }
    }


    private void joinGame(JsonObject json){
        String url = json.get("url").getAsString();
        String enemy = json.get("enemy").getAsString();
        int enemyRating = json.get("enemyRating").getAsInt();
        int rating = json.get("rating").getAsInt();
        int xPlayer = json.get("xPlayer").getAsInt();
        int yPlayer = json.get("yPlayer").getAsInt();
        int xEnemy = json.get("xEnemy").getAsInt();
        int yEnemy = json.get("yEnemy").getAsInt();
        int length = json.get("length").getAsInt();
        client.joinGame(url, enemy, enemyRating, rating, new Point(xPlayer, yPlayer), new Point(xEnemy, yEnemy), length);
    }

    @Override
    public int loginPlayer(String name, String hash) throws IOException, InterruptedException {
        JsonObject json = new JsonObject();
        json.addProperty("login", true);
        json.addProperty("username", name);
        json.addProperty("password", hash);
        manager.getBasicRemote().sendText(json.toString());
        return playerId.poll(5, TimeUnit.SECONDS);
    }

    @Override
    public boolean registerPlayer(String name, String hash) throws IOException, InterruptedException {
        JsonObject json = new JsonObject();
        json.addProperty("register", true);
        json.addProperty("username", name);
        json.addProperty("password", hash);
        manager.getBasicRemote().sendText(json.toString());
        return register.poll(5, TimeUnit.SECONDS);
    }

    @Override
    public void updatePlayer() throws IOException, InterruptedException {
        JsonObject json = new JsonObject();
        json.addProperty("stats", true);

        manager.getBasicRemote().sendText(json.toString());
        JsonObject result = stats.poll(5, TimeUnit.SECONDS);
        client.setGames(result.get("games").getAsInt());
        client.setWins(result.get("wins").getAsInt());
    }

    @Override
    public void sendMessagePlayer(String message) throws IOException {
        JsonObject json = new JsonObject();
        json.addProperty("message", true);
        json.addProperty("text", message);
        manager.getBasicRemote().sendText(json.toString());
    }

    @Override
    public void joinQueue() throws IOException{
        JsonObject json = new JsonObject();
        json.addProperty("queue", true);
        manager.getBasicRemote().sendText(json.toString());
    }

    private void receiveMessageOther(JsonObject json){
        String name = json.get("name").getAsString();
        String message = json.get("text").getAsString();
        client.showMessageOther(name, message);
    }


}
