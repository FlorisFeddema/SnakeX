package snakex.model.game;

import snakex.gameserver.GameManagerEndPoint;
import snakex.gameserver.IsGameManagerEndPoint;
import snakex.model.shared.Point;
import snakex.model.shared.Snake;
import snakex.model.enums.GameResult;
import snakex.model.enums.MoveDirection;
import com.google.gson.JsonObject;
import snakex.rest.IsGameRestEndPoint;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

public class GameModel implements IsGameModel {

    private IsGameRestEndPoint rest;
    private IsGameManagerEndPoint manager;
    private List<Snake> players;
    private Timer gameTimer;
    private int gameSteps;
    private Point powerUp;

    public GameModel(String url, IsGameRestEndPoint rest) {
        manager = new GameManagerEndPoint(url);
        players = new ArrayList<>();
        gameTimer = new Timer();
        gameSteps = 0;
        this.rest = rest;
    }

    @Override
    public void setPlayer(Snake snake) {
        players.add(snake);
    }

    @Override
    public void connectPlayer(int id, Session session) {
        for (Snake snake : players){
            if (snake.getId() == id){
                snake.setSession(session);
                break;
            }
        }
        checkAllReady();
    }

    @Override
    public void updateDirection(MoveDirection direction, Session session) {
        for (Snake snake : players){
            if (snake.getSession().getId().equals(session.getId())){
                snake.setDirection(direction);
                return;
            }
        }
    }

    private void spawnPowerUp(){
        boolean done = false;
        Random random = new Random();
        Point point = null;
        while (!done){
            int x = random.nextInt(20);
            int y = random.nextInt(20);
            point = new Point(x, y);
            done = !players.get(0).isOnSnake(point) && !players.get(1).isOnSnake(point);
        }

        powerUp = point;

        for (Snake snake : players){
            JsonObject json = new JsonObject();
            json.addProperty("powerUp", true);
            json.addProperty("x", powerUp.getX());
            json.addProperty("y", powerUp.getY());
            try {
                snake.getSession().getBasicRemote().sendText(json.toString());
            } catch (IOException e) {
                //ignore
            }
        }

    }

    private void checkAllReady(){
        for (Snake snake : players){
            if (snake.getSession() == null)
                return;
        }

        spawnPowerUp();

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                gameTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        moveSnakes();
                    }
                }, 0, 1200);
            }
        }, 2000);
    }

    private void sendPowerUpPickup(Snake player, Snake enemy){
        JsonObject json = new JsonObject();
        json.addProperty("pickup", true);
        JsonObject json2 = new JsonObject();
        json2.addProperty("pickup", false);
        try {
            player.getSession().getBasicRemote().sendText(json.toString());
            enemy.getSession().getBasicRemote().sendText(json2.toString());
        } catch (IOException e) {
            //ignore
        }
        spawnPowerUp();
    }

    private void moveSnakes(){
        for (int i = 0; i < players.size(); i++) {
            Snake snake = players.get(i);
            snake.move(snake.getDirection());
            if (snake.isOnSnake(powerUp)) {
                sendPowerUpPickup(players.get(i), players.get(1-i));
            }
        }

        for (Snake snake : players){
            Point point = snake.getLastPosition();
            if (!point.isOnGrid()){
                snake.setAlive(false);
                break;
            } else {
                for (Snake s : players){
                    if (s.isOnSnake(point) && s != snake){
                        snake.setAlive(false);
                        break;
                    }
                }
            }
        }
        gameSteps++;
        checkAlive();
        sendMoves();
    }

    private void checkAlive(){
        for (Snake snake : players){
            if (!snake.isAlive()){
                gameTimer.cancel();
            }
        }
    }

    private void sendMoves(){
        for (int i = 0; i < players.size(); i++) {
            GameResult result = GameResult.NONE;
            if (!players.get(i).isAlive()){
               if (players.get(1-i).isAlive()){
                   result = GameResult.LOSS;
               } else {
                   result = GameResult.DRAW;
               }
            } else {
                if (!players.get(1-i).isAlive()){
                    result = GameResult.WON;
                }
            }
            players.get(i).setResult(result);
            JsonObject json = new JsonObject();
            json.addProperty("move", true);
            json.addProperty("player", players.get(i).getDirection().toString());
            json.addProperty("enemy", players.get(1-i).getDirection().toString());
            json.addProperty("result", result.toString());

            try {
                players.get(i).getSession().getBasicRemote().sendText(json.toString());
            } catch (IOException e) {
                //ignore
            }
        }
        checkGameEnd();
    }

    private void checkGameEnd(){
        for (Snake snake : players){
            if (snake.getResult().equals(GameResult.NONE)){
                return;
            }
        }

        float max = 1.2f;
        float min = 0.8f;

        int steps = gameSteps;
        float factor = 50;
        Snake winner;
        Snake loser;

        if (players.get(0).getResult() == GameResult.DRAW){
            //Handle draw
            loser = (players.get(0).getRating() > players.get(1).getRating() ? players.get(0) : players.get(1));
            winner = (players.get(0).getRating() > players.get(1).getRating() ? players.get(1) : players.get(0));
            factor = factor/10;
        } else {
            loser = (players.get(0).getResult() == GameResult.WON ? players.get(1) : players.get(0));
            winner = (players.get(0).getResult() == GameResult.WON ? players.get(0) : players.get(1));
        }

        float diffFactor = loser.getRating()/(float)winner.getRating();

        diffFactor = Math.max(diffFactor, min);
        diffFactor = Math.min(diffFactor, max);

        int value = (int)(diffFactor*factor)/steps;


        rest.updateRating(winner.getId(), value, true);
        rest.updateRating(loser.getId(), -value, false);

        manager.endGame(winner.getId(), loser.getId());

        resetServer();
    }

    private void resetServer(){
        players = new ArrayList<>();
        gameTimer = new Timer();
        gameSteps = 0;
    }
}
