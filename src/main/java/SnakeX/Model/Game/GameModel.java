package SnakeX.Model.Game;

import SnakeX.GameServer.GameManagerEndPoint;
import SnakeX.GameServer.IsGameManagerEndPoint;
import SnakeX.Model.Shared.Point;
import SnakeX.Model.Shared.Snake;
import SnakeX.Model.enums.MoveDirection;
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
                }, 0, 1000);
            }
        }, 2000);
    }

    private void moveSnakes(){
        for (Snake snake : players){
            Point point = snake.move(snake.getDirection());
            if (!point.isOnGrid()){
                snake.setAlive(false);
                break;
            } else {
                for (Snake s : players){
                    if (s.isOnSnake(point)){
                        snake.setAlive(false);
                        break;
                    }
                }
            }

        }
        sendMoves();
    }

    private void sendMoves(){
        for (int i = 0; i < players.size(); i++) {
            JsonObject json = new JsonObject();
            json.addProperty("move", true);
            json.addProperty("player", players.get(i).getDirection().toString());
            json.addProperty("enemy", players.get(1-i).getDirection().toString());
            json.addProperty("alivePlayer", players.get(i).isAlive());
            json.addProperty("aliveEnemy", players.get(1-i).isAlive());

            try {
                players.get(i).getSession().getBasicRemote().sendText(json.toString());
            } catch (IOException e) {
                //ignore
            }
        }
        System.out.println("Move done");
    }

}
