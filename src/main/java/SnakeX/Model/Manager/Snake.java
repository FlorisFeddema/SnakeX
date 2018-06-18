package SnakeX.Model.Manager;

import SnakeX.Client.Logic.Point;
import SnakeX.Model.enums.MoveDirection;
import javafx.scene.paint.Color;
import org.eclipse.persistence.internal.libraries.antlr.runtime.tree.UnBufferedTreeNodeStream;

import java.util.ArrayList;
import java.util.List;

public class Snake {
    private Color color;
    private String name;
    private int rating;


    public String getName() {
        return name;
    }

    public int getRating() {
        return rating;
    }

    public Color getColor() {
        return color;
    }


    private int length;
    private int maxLength;
    private List<Point> positions;

    public List<Point> getPositions() {
        return positions;
    }

    public Point getLastPosition() {
        return positions.get(positions.size() - 1);
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

    public Snake(int maxLength, Point point) {
        this.length = 1;
        this.maxLength = maxLength;
        this.positions = new ArrayList<>();
        positions.add(point);
    }

    public void move(MoveDirection direction){
        int x = 0;
        int y = 0;
        switch (direction) {
            case Up:
                x = 1;
                break;
            case Down:
                x = -1;
                break;
            case Left:
                y = -1;
                break;
            case Right:
                y = 1;
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
    }




}
