package SnakeX.Model.Shared;

import SnakeX.Model.enums.MoveDirection;
import javafx.scene.paint.Color;

import javax.websocket.Session;
import java.util.ArrayList;
import java.util.List;

public class Snake {
    private Color color;
    private String name;
    private int rating;

    private int id;

    private Session session;


    private boolean alive;

    private int length;
    private int maxLength;
    private List<Point> positions;
    private MoveDirection direction;

    public boolean isAlive() {
        return alive;
    }

    public void setAlive(boolean alive) {
        this.alive = alive;
    }

    public Session getSession() {
        return session;
    }

    public void setSession(Session session) {
        this.session = session;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public Color getColor() {
        return color;
    }

    public List<Point> getPositions() {
        return positions;
    }

    public Point getLastPosition() {
        return positions.get(positions.size() - 1);
    }

    public MoveDirection getDirection() {
        return direction;
    }

    public void setDirection(MoveDirection direction) {
        this.direction = direction;
    }

    public Snake(Color color, int maxLength, Point point, String name, int rating) {
        this.color = color;
        this.length = 1;
        this.maxLength = maxLength;
        this.positions = new ArrayList<>();
        positions.add(point);
        this.name = name;
        this.rating = rating;
        id = Integer.MIN_VALUE;
        direction = MoveDirection.Up;
        alive = true;
    }

    public Snake(int maxLength, Point point) {
        this.length = 1;
        this.maxLength = maxLength;
        this.positions = new ArrayList<>();
        positions.add(point);
        id = Integer.MIN_VALUE;
        direction = MoveDirection.Up;
        alive = true;
    }

    public Snake(int maxLength, Point point, int id) {
        this.length = 1;
        this.id = id;
        this.maxLength = maxLength;
        this.positions = new ArrayList<>();
        positions.add(point);

        direction = MoveDirection.Up;
        alive = true;

    }

    public void grow(){
        maxLength++;
    }

    public Point move(MoveDirection direction){
        int x = 0;
        int y = 0;
        switch (direction) {
            case Up:
                y = -1;
                break;
            case Down:
                y = 1;
                break;
            case Left:
                x = -1;
                break;
            case Right:
                x = 1;
                break;
        }
        Point last = getLastPosition();

        Point next = new Point(last.getX() + x, last.getY() + y);

        if (length < maxLength){
            length++;
        } else {
            positions.remove(0);
        }
        positions.add(next);
        return next;
    }

    public boolean isOnSnake(Point point){
        for (Point p : positions){
            if (p.x == point.x && p.y == point.y)
                return true;
        }
        return false;
    }




}
