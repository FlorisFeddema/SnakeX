package SnakeX.Model.Game;

import SnakeX.GameServer.GameManagerEndPoint;
import SnakeX.GameServer.IsGameManagerEndPoint;
import SnakeX.Model.Shared.Point;
import SnakeX.Model.Shared.Snake;
import SnakeX.Model.enums.GameResult;
import SnakeX.Model.enums.MoveDirection;
import com.google.gson.JsonObject;

import javax.websocket.Session;
import javax.xml.transform.Result;
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
                System.out.println("We did it");
                break;
            } else {
                for (Snake s : players){
                    if (s.isOnSnake(point) && s != snake){
                        System.out.println("We did it");
                        snake.setAlive(false);
                        break;
                    }
                }
            }
        }
        sendMoves();
        for (Snake snake : players){
            if (!snake.isAlive()){
                gameTimer.cancel();
            }
        }
    }

    private void sendMoves(){
        for (int i = 0; i < players.size(); i++) {
            GameResult result = GameResult.None;
            if (!players.get(i).isAlive()){
               if (players.get(1-i).isAlive()){
                   result = GameResult.Loss;
               } else {
                   result = GameResult.Draw;
               }
            } else {
                if (!players.get(1-i).isAlive()){
                    result = GameResult.Won;
                }
            }
            JsonObject json = new JsonObject();
            json.addProperty("move", true);
            json.addProperty("player", players.get(i).getDirection().toString());
            json.addProperty("enemy", players.get(1-i).getDirection().toString());
            json.addProperty("result", result.toString());
            System.out.println(players.get(i).isAlive());

            try {
                players.get(i).getSession().getBasicRemote().sendText(json.toString());
            } catch (IOException e) {
                //ignore
            }
        }
    }

}
