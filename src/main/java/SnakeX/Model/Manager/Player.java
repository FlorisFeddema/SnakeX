package SnakeX.Model.Manager;

import SnakeX.Model.Shared.Snake;
import SnakeX.Model.enums.PlayerStatus;

import javax.websocket.Session;

public class Player {


    private Session session;
    private String name;
    private int id;
    private int games;
    private int wins;
    private int rating;
    private PlayerStatus status;
    private Snake snake;

    public Snake getSnake() {
        return snake;
    }

    public void setSnake(Snake snake) {
        this.snake = snake;
    }

    public int getGames() {
        return games;
    }

    public void setGames(int games) {
        this.games = games;
    }

    public int getWins() {
        return wins;
    }

    public void setWins(int wins) {
        this.wins = wins;
    }

    public Session getSession() {
        return session;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    public PlayerStatus getStatus() {
        return status;
    }

    public void setStatus(PlayerStatus status) {
        this.status = status;
    }

    public Player(Session session) {
        this.session = session;
        this.id = Integer.MIN_VALUE;
        this.games = 0;
        this.wins = 0;
        this.rating = 1500;
        this.status = PlayerStatus.Lobby;
    }
    
}


