package snakex.client.logic;

import snakex.client.ui.IsGameController;
import snakex.client.ui.IsMainController;
import snakex.model.shared.Point;
import snakex.model.shared.Snake;
import snakex.model.enums.GameResult;
import snakex.model.enums.MoveDirection;
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
    private Point powerUp;

    @Override
    public Point getPowerUp() {
        return powerUp;
    }


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
        int playerId =  managerEndPoint.loginPlayer(name, hash);
        id = playerId;
        return playerId;
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
        if (MoveDirection.getOpposite(direction) != player.getDirection()){
            gameEndPoint.sendDirection(direction);
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
    public void joinGame(String url, String enemyName, int enemyRating, int rating, Point player, Point enemy, int length) {
        gameUrl = url;
        this.player = new Snake(Color.BLACK, length, player, "You", rating);
        this.enemy = new Snake(Color.RED, length, enemy, enemyName, enemyRating);
        mainController.joinGame();
    }

    @Override
    public void move(MoveDirection playerDirection, MoveDirection enemyDirection, GameResult result) {
        if (result == GameResult.NONE){
            player.setDirection(playerDirection);
            player.move(playerDirection);
            enemy.move(enemyDirection);
            gameController.move(new Snake[] {player, enemy});
        } else {
            gameEndPoint.disconnect();
            gameEndPoint = null;
            if (result == GameResult.WON){
                gameController.showWin();
            } else if (result == GameResult.LOSS){
                gameController.showLoss();
            } else {
                gameController.showDraw();
            }
        }
    }

    @Override
    public void drawPowerUp(int x, int y){
        powerUp = new Point(x, y);
    }

    @Override
    public void growSnake(boolean isPlayer) {
        if (isPlayer){
            player.grow();
        } else {
            enemy.grow();
        }
    }
}
