package SnakeX.Model.Manager;

import javax.websocket.Session;

public class ServerEntry {
    Session session;
    String url;
    int port;

    public ServerEntry(Session session, String url, int port) {
        this.session = session;
        this.url = url;
        this.port = port;
    }
}
