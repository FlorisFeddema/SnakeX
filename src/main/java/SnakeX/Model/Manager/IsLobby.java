package SnakeX.Model.Manager;

import javax.websocket.Session;

public interface IsLobby {
    Player getPlayer(Session session);

    void addPlayer(Player player);

    void removePlayer(Session session);

    Player updatePlayer(Session session);

    boolean registerPlayer(String username, String password);

    int loginPlayer(String username, String password, Session session);

    void receiveMessage(String text, Session session);

    void joinQueue(Session session);

    void connectServer(Session session, String url);
}
