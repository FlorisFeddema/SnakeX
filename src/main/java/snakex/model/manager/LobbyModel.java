package snakex.model.manager;

import snakex.model.shared.Point;
import snakex.model.shared.Snake;
import snakex.model.enums.PlayerStatus;
import snakex.model.enums.ServerStatus;
import snakex.rest.IsRestEndpoint;
import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

public class LobbyModel implements IsLobby {
    private static final int GRIDSIZE = 20;

    public IsChat getChat() {
        return chat;
    }

    private IsChat chat;

    public Set<Player> getPlayers() {
        return players;
    }

    private Set<Player> players;
    private IsRestEndpoint rest;
    private Queue queue;
    private Timer queueTimer;

    public Set<ServerEntry> getServers() {
        return servers;
    }

    private Set<ServerEntry> servers;

    public LobbyModel(IsRestEndpoint rest){
        players = new HashSet<>();
        this.rest = rest;
        chat = new Chat();
        queue = new Queue();
        servers = new HashSet<>();
        queueTimer = new Timer();
        queueTimer.schedule(new TimerTask() {
            @Override
            public void run() {
                checkQueue();
            }
        }, 0, 5000);
    }

    public void connectServer(Session session, String url){
        ServerEntry entry = new ServerEntry(session, url);
        servers.add(entry);
    }

    public void checkQueue(){
        Player[] playersGame;
        while ((playersGame = queue.searchMatch())[0] != null){
            if (!joinGame(playersGame)){
                for (Player i : playersGame){
                    i.setStatus(PlayerStatus.QUEUE);
                }
                return;
            }
        }
    }

    private boolean joinGame(Player[] players){
        ServerEntry server = null;
        for (ServerEntry i : servers){
            if (i.getStatus() == ServerStatus.FREE){
                server = i;
                break;
            }
        }

        if (server == null){
            return false;
        }

        Random random = new Random();
        for (int i = 0; i < players.length; i++) {
            players[i].setStatus(PlayerStatus.PLAYING);
            Player player = players[i];
            int x = 1 + random.nextInt(GRIDSIZE/2 - 2) + 10*i;
            int y = 1 + random.nextInt(GRIDSIZE/2 - 2) + 10*i;
            Point point = new Point(x, y);
            Snake snake = new Snake(5, point);
            player.setSnake(snake);
        }


        for (int i = 0; i < players.length; i++) {
            try {
                JsonObject json = new JsonObject();
                json.addProperty("gameserver", true);
                json.addProperty("url", server.getUrl());
                json.addProperty("enemy", players[1-i].getName());
                json.addProperty("enemyRating", players[1-i].getRating());
                json.addProperty("rating", players[i].getRating());
                json.addProperty("xPlayer", players[i].getSnake().getLastPosition().getX());
                json.addProperty("yPlayer", players[i].getSnake().getLastPosition().getY());
                json.addProperty("xEnemy", players[1-i].getSnake().getLastPosition().getX());
                json.addProperty("yEnemy", players[1-i].getSnake().getLastPosition().getY());
                json.addProperty("length", 5);
                players[i].getSession().getBasicRemote().sendText(json.toString());
            } catch (IOException|NullPointerException e) {
                //ignore
            }
            queue.removeEntry(players[i]);
        }
        server.setStatus(ServerStatus.BUSY);

        for (int i = 0; i< players.length; i++){
            try {
                JsonObject json = new JsonObject();
                json.addProperty("spawn", true);
                json.addProperty("id", players[i].getId());
                json.addProperty("x", players[i].getSnake().getLastPosition().getX());
                json.addProperty("y", players[i].getSnake().getLastPosition().getY());
                json.addProperty("rating", players[i].getRating());
                json.addProperty("length", 5);

                server.getSession().getBasicRemote().sendText(json.toString());
            } catch (IOException|NullPointerException e) {
                //ignore
            }
        }


        return true;
    }


    public Player getPlayer(Session session){
        for (Player i : players){
            if (i.getSession().getId() == session.getId()){
                return i;
            }
        }
        return null;
    }

    @Override
    public void addPlayer(Player player) {
        players.add(player);
    }

    @Override
    public void removePlayer(Session session) {
        players.removeIf(p -> p.getSession() == session);
        servers.removeIf(p -> p.getSession() == session);
    }

    @Override
    public Player updatePlayer(Session session) {
        return rest.updatePlayer(getPlayer(session));
    }

    @Override
    public boolean registerPlayer(String username, String password) {
        return rest.registerPlayer(username,  password);
    }

    @Override
    public int loginPlayer(String username, String password, Session session) {
        int id = rest.loginPlayer(username,  password);
        if (id > 0){
            for (Player player : players){
                if (player.getId() == id) return Integer.MIN_VALUE;
            }
            Player player = new Player(session);
            player.setName(username);
            player.setId(id);
            addPlayer(player);
        }

        return id;
    }

    @Override
    public void receiveMessage(String text, Session session) {
        Message message = new Message(text, getPlayer(session));
        chat.addMessage(message);

        JsonObject json = new JsonObject();
        json.addProperty("message", true);
        json.addProperty("name", message.getPlayer().getName());
        json.addProperty("text", message.getText());
        try {
            sendChatOther(json, message.getPlayer());
        } catch (IOException e) {
            //ignore
        }
    }

    @Override
    public void joinQueue(Session session) {
        Player player = getPlayer(session);
        queue.addEntry(player);
    }

    private void sendChatOther(JsonObject message, Player player) throws IOException {
        for (Player i : players){
            if (!i.getSession().getId().equals(player.getSession().getId()) && i.getStatus() != PlayerStatus.PLAYING){
                i.getSession().getBasicRemote().sendText(message.toString());
            }
        }
    }

}
