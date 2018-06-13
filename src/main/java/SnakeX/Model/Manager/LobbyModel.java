package SnakeX.Model.Manager;

import SnakeX.Model.enums.PlayerStatus;
import SnakeX.REST.IsRestEndpoint;
import SnakeX.REST.RestEndPoint;
import SnakeX.Shared.ConsoleColors;
import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

public class LobbyModel implements IsLobby {
    private IsChat chat;
    private Set<Player> players;
    private IsRestEndpoint rest;
    private Queue queue;
    private Timer queueTimer;
    private Set<ServerEntry> servers;

    public LobbyModel(){
        players = new HashSet<>();
        rest = new RestEndPoint();
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

    private void checkQueue(){
        Player[] players = null;
        while ((players = queue.searchMatch()) != null){
            joinGame(players);
        }
    }

    private void joinGame(Player[] players){

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
        players.removeIf(p -> p.getSession().getId().equals(session.getId()));
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
            System.out.println(ConsoleColors.BLUE + "Manager: Sending it to people");

            sendChatOther(json, message.getPlayer());
        } catch (IOException e) {
            //ignore
        }
    }

    @Override
    public void joinQueue(Session session) {
        Player player = getPlayer(session);
        queue.addEntry(player);
        System.out.println(ConsoleColors.BLUE + "Manager: You joined queue bby");
    }

    private void sendChatOther(JsonObject message, Player player) throws IOException {
        for (Player i : players){
            if (!i.getSession().getId().equals(player.getSession().getId()) && i.getStatus() != PlayerStatus.Playing){
                i.getSession().getBasicRemote().sendText(message.toString());
            }
        }
    }

}
