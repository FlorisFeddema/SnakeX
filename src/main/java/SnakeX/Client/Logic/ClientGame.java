package SnakeX.Client.Logic;

import SnakeX.Client.UI.IsGameController;
import SnakeX.Client.UI.IsMainController;
import SnakeX.Model.Shared.Point;
import SnakeX.Model.Shared.Snake;
import SnakeX.Model.enums.GameResult;
import SnakeX.Model.enums.MoveDirection;
import javafx.scene.paint.Color;

import javax.lang.model.util.ElementScanner6;
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
        if (direction.isHorizonal() ^ player.getDirection().isHorizonal()){
            gameEndPoint.sendDirection(direction);
            player.setDirection(direction);
        }
    }

    @Override
    public void connectGame() {
        gameEndPoint = new ClientGameEndPoint(this, gameUrl, id);

    }

    @Override
    public void showMessageOther(String name, String message){
        mainController.addMessageOther(name, message);
    }

    @Override
    public void joinGame(String url, String enemy, int enemyRating, int rating, int xPlayer, int yPlayer, int xEnemy, int yEnemy, int length) {
        gameUrl = url;
        player = new Snake(Color.BLACK, length, new Point(xPlayer, yPlayer), "You", rating);
        this.enemy = new Snake(Color.RED, length, new Point(xEnemy, yEnemy), enemy, enemyRating);
        mainController.joinGame();
    }

    @Override
    public void move(MoveDirection playerDirection, MoveDirection enemyDirection, GameResult result) {
        System.out.println(result);
        if (result == GameResult.None){
            player.move(playerDirection);
            enemy.move(enemyDirection);
            gameController.move(new Snake[] {player, enemy});
        } else {
            if (result == GameResult.Won){
                gameController.showWin();
            } else if (result == GameResult.Loss){
                gameController.showLoss();
            } else {
                gameController.showDraw();
            }
        }
    }
}
