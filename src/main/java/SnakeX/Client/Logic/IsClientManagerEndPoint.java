package SnakeX.Client.Logic;

import java.io.IOException;

public interface IsClientManagerEndPoint {

    int loginPlayer(String name, String hash) throws IOException, InterruptedException;

    boolean registerPlayer(String name, String hash) throws IOException, InterruptedException;

    void updatePlayer() throws IOException, InterruptedException;

    void sendMessagePlayer(String message) throws IOException;

    void joinQueue() throws IOException;
}
