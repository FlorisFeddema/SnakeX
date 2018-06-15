package SnakeX.Client.Logic;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private Color color;
    private int length;
    private int maxLength;
    private List<Point> positions;
    private String name;
    private int rating;

    public Color getColor() {
        return color;
    }

    public List<Point> getPositions() {
        return positions;
    }

    public Snake(Color color, int maxLength, Point point, String name, int rating) {
        this.color = color;
        this.length = 1;
        this.maxLength = maxLength;
        this.positions = new ArrayList<>();
        positions.add(point);
        this.name = name;
        this.rating = rating;
    }

    public void move(Point point){
        if (length < maxLength){
            length++;
        } else {
            positions.remove(0);
        }
        positions.add(point);
    }
}
