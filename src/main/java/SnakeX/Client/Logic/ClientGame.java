package SnakeX.Client.Logic;

import SnakeX.Client.UI.IsGameController;
import SnakeX.Client.UI.IsMainController;
import SnakeX.Model.Manager.Snake;
import SnakeX.Model.enums.MoveDirection;
import javafx.scene.paint.Color;

import java.io.IOException;

public class ClientGame implements IsClient, IsControllerClient {

    private IsClientManagerEndPoint managerEndPoint;
    private IsClientGameEndPoint gameEndPoint;
    private IsMainController mainController;
    private IsGameController gameController;
    private int wins;
    private int games;
    private String gameUrl;
    private int id;
    private Snake player;
    private Snake enemy;


    public Snake getPlayer() {
        return player;
    }

    public Snake getEnemy() {
        return enemy;
    }

    public void setGameController(IsGameController gameController) {
        this.gameController = gameController;
    }

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
    public void joinGame(String url, String enemy, int enemyRating, int rating, int xPlayer, int yPlayer, int xEnemy, int yEnemy) {
        gameUrl = url;
        player = new Snake(Color.BLACK, 5, new Point(xPlayer, yPlayer), "You", rating);
        this.enemy = new Snake(Color.RED, 5, new Point(xEnemy, yEnemy), enemy, enemyRating);
        gameEndPoint = new ClientGameEndPoint(this, url);
        mainController.joinGame();
    }
}
