package SnakeX.GameServer;

import SnakeX.Shared.ConsoleColors;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;


@ServerEndpoint(value = "/snake/game/")
public class GameEndPoint {

    @OnOpen
    public void connect(Session session){
        System.out.println(ConsoleColors.PURPLE + "Game: we got a new mate");
    }

    @OnClose
    public void close(Session session){
    }

    @OnError
    public void onError(Throwable cause, Session session) {
        System.out.println(ConsoleColors.PURPLE + "Game: A client got an error");
    }

    @OnMessage
    public void onMessageReceived(String message, Session session) {

    }
}
