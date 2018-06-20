package snakex.model.shared;

import snakex.model.enums.GameResult;
import snakex.model.enums.MoveDirection;
import javafx.scene.paint.Color;
import org.junit.Before;
import org.junit.Test;

import javax.websocket.Session;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;

public class SnakeTest {

    Snake snake1;
    Snake snake2;
    Snake snake3;
    Point point;

    @Before
    public void setUp() throws Exception {
        point = new Point(2, 5 );
        snake1 = new Snake(Color.BLACK, 5, point, "test", 1500);
        snake2 = new Snake(5, point);
        snake3 = new Snake(5, point, 2, 1300);
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
        snake1.move(MoveDirection.UP);

        assertEquals(2, snake1.getLastPosition().getX());
        assertEquals(4, snake1.getLastPosition().getY());

        snake1.move(MoveDirection.DOWN);

        assertEquals(2, snake1.getLastPosition().getX());
        assertEquals(5, snake1.getLastPosition().getY());

        snake1.move(MoveDirection.LEFT);

        assertEquals(1, snake1.getLastPosition().getX());
        assertEquals(5, snake1.getLastPosition().getY());

        snake1.move(MoveDirection.RIGHT);

        assertEquals(2, snake1.getLastPosition().getX());
        assertEquals(5, snake1.getLastPosition().getY());
    }

    @Test
    public void isAlive() {
        assertTrue(snake1.isAlive());
        snake1.setAlive(false);
        assertFalse(snake1.isAlive());
    }

    @Test
    public void getSession() {
        Session session = mock(Session.class);
        snake1.setSession(session);
        assertSame(session, snake1.getSession());
        assertNull(snake2.getSession());
    }

    @Test
    public void getId() {
        assertEquals(2, snake3.getId());
    }

    @Test
    public void getResult() {
        assertEquals(GameResult.NONE, snake1.getResult());
        snake1.setResult(GameResult.WON);
        assertEquals(GameResult.WON, snake1.getResult());
    }

    @Test
    public void getDirection() {
        assertEquals(MoveDirection.UP, snake1.getDirection());
        snake1.setDirection(MoveDirection.DOWN);
        assertEquals(MoveDirection.DOWN, snake1.getDirection());
        snake1.setDirection(MoveDirection.LEFT);
        assertEquals(MoveDirection.LEFT, snake1.getDirection());
    }

    @Test
    public void grow() {
        int length = snake1.getMaxLength();
        snake1.grow();
        assertEquals(length + 1, snake1.getMaxLength());
    }

    @Test
    public void isOnSnake() {
        Point point2 = new Point(15, 8);
        assertFalse(snake1.isOnSnake(point2, false));
        assertTrue(snake1.isOnSnake(point, false));
    }
}