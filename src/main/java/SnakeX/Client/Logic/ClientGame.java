package SnakeX.Client.Logic;

import SnakeX.Client.UI.IsMainController;

import java.io.IOException;

public class ClientGame implements IsClient, IsControllerClient {

    private IsClientManagerEndPoint managerEndPoint;
    private IsClientGameEndPoint gameEndPoint;
    private int id;
    private IsMainController mainController;
    private int wins;
    private int games;

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
        id = Integer.MIN_VALUE;
        wins = Integer.MIN_VALUE;
        games = Integer.MIN_VALUE;
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
    public void showMessageOther(String name, String message){
        mainController.addMessageOther(name, message);
    }
}
