package snakex.model.game;

import snakex.gameserver.GameManagerEndPoint;
import snakex.gameserver.IsGameManagerEndPoint;
import snakex.model.shared.Point;
import snakex.model.shared.Snake;
import snakex.model.enums.GameResult;
import snakex.model.enums.MoveDirection;
import com.google.gson.JsonObject;

import javax.websocket.Session;
import java.io.IOException;
import java.util.*;

public class GameModel implements IsGameModel {

    private IsGameManagerEndPoint manager;
    private List<Snake> players;
    private Timer gameTimer;

    public GameModel(String url) {
        manager = new GameManagerEndPoint(url);
        players = new ArrayList<>();
        gameTimer = new Timer();
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

    private void checkAllReady(){
        for (Snake snake : players){
            if (snake.getSession() == null)
                return;
        }

        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                gameTimer.schedule(new TimerTask() {
                    @Override
                    public void run() {
                        moveSnakes();
                    }
                }, 0, 1500);
            }
        }, 2000);
    }

    private void moveSnakes(){
        for (Snake snake : players){
            snake.move(snake.getDirection());
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
        sendMoves();
        checkAlive();
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
            if (snake.getResult() == GameResult.NONE)
                return;
        }

        //TODO GAME DONE BBY HANDLE THIS PLEAS




        resetServer();
    }

    private void resetServer(){
        //TODO RESET SERVER WHEN DONE
    }
}
