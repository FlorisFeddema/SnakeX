package SnakeX.Model.Manager;

import SnakeX.Model.Shared.Point;
import SnakeX.Model.Shared.Snake;
import SnakeX.Model.enums.MoveDirection;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class SnakeTest {

    Snake snake1;
    Snake snake2;
    Point point;

    @Before
    public void setUp() throws Exception {
        point = new Point(2, 5 );
        snake1 = new Snake(Color.BLACK, 5, point, "test", 1500);
        snake2 = new Snake(5, point);
    }

    @Test
    public void getName() {
        assertEquals("test", snake1.getName());
    }

    @Test
    public void getRating() {
        assertEquals(1500, snake1.getRating());
    }

    @Test
    public void getColor() {
        assertEquals(Color.BLACK, snake1.getColor());
    }

    @Test
    public void getPositions() {
        assertSame(point, snake1.getPositions().get(0));
        assertEquals(1, snake1.getPositions().size());
        assertSame(point, snake2.getPositions().get(0));
        assertEquals(1, snake2.getPositions().size());

    }

    @Test
    public void getLastPosition() {
        assertSame(point, snake1.getLastPosition());
        assertSame(point, snake2.getLastPosition());

    }

    @Test
    public void move() {
        snake1.move(MoveDirection.Up);

        assertEquals(2, snake1.getLastPosition().getX());
        assertEquals(4, snake1.getLastPosition().getY());

        snake1.move(MoveDirection.Down);

        assertEquals(2, snake1.getLastPosition().getX());
        assertEquals(5, snake1.getLastPosition().getY());

        snake1.move(MoveDirection.Left);

        assertEquals(1, snake1.getLastPosition().getX());
        assertEquals(5, snake1.getLastPosition().getY());

        snake1.move(MoveDirection.Right);

        assertEquals(2, snake1.getLastPosition().getX());
        assertEquals(5, snake1.getLastPosition().getY());
    }
}