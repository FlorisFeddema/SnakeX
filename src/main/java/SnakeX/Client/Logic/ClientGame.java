package SnakeX.Client.Logic;

import SnakeX.Client.UI.IsMainController;
import SnakeX.Model.enums.MoveDirection;

import java.io.IOException;

public class ClientGame implements IsClient, IsControllerClient {

    private IsClientManagerEndPoint managerEndPoint;
    private IsClientGameEndPoint gameEndPoint;
    private IsMainController mainController;
    private int wins;
    private int games;
    private String gameUrl;
    private int id;

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public void setMainController(IsMainController controller) {
        this.mainController = controller;
    }

    public ClientGame(){
        managerEndPoint = new ClientManagerEndPoint(this);
        wins = Integer.MIN_VALUE;
        games = Integer.MIN_VALUE;
        id = Integer.MIN_VALUE;
    }

    @Override
    public int loginPlayer(String name, String password) throws IOException, InterruptedException {
        String hash = Hash.getHash(password);
        int id =  managerEndPoint.loginPlayer(name, hash);
        this.id = id;
        return id;
    }

    @Override
    public boolean registerPlayer(String name, String password) throws IOException, InterruptedException {
        String hash = Hash.getHash(password);
        return managerEndPoint.registerPlayer(name, hash);
    }

    @Override
    public void updatePlayerStats() throws IOException, InterruptedException {
        managerEndPoint.updatePlayer();
    }

    @Override
    public void sendMessagePlayer(String message) throws IOException {
        managerEndPoint.sendMessagePlayer(message);
    }

    @Override
    public void joinQueue() throws IOException {
        managerEndPoint.joinQueue();
    }

    @Override
    public void move(MoveDirection direction) {
        //TODO
    }

    @Override
    public void showMessageOther(String name, String message){
        mainController.addMessageOther(name, message);
    }

    @Override
    public void joinGame(String url) {
        gameUrl = url;
        gameEndPoint = new ClientGameEndPoint(this, url);
        mainController.joinGame();
    }
}
