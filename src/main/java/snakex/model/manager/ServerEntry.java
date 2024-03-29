package snakex.model.manager;

import snakex.model.enums.ServerStatus;

import javax.websocket.Session;

public class ServerEntry {
    public Session getSession() {
        return session;
    }

    public String getUrl() {
        return url;
    }

    private Session session;
    private String url;

    public ServerStatus getStatus() {
        return status;
    }

    public void setStatus(ServerStatus status) {
        this.status = status;
    }

    private ServerStatus status;


    public ServerEntry(Session session, String url) {
        this.session = session;
        this.url = url;
        status = ServerStatus.FREE;
    }
}
