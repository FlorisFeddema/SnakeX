package SnakeX.Model.Shared;

public class Point {
    int x;

    int y;

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Point(int x, int y) {
        this.x = x;
        this.y = y;
    }

    public boolean isOnGrid(){
        boolean rx = x < 20 && x > 0;
        boolean ry = y < 20 && y > 0;
        return rx && ry;
    }
}
