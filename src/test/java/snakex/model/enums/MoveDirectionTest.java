package snakex.model.enums;

import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class MoveDirectionTest {

    MoveDirection up;
    MoveDirection down;
    MoveDirection left;
    MoveDirection right;

    @Before
    public void setUp() throws Exception {
        up = MoveDirection.UP;
        down = MoveDirection.DOWN;
        left = MoveDirection.LEFT;
        right = MoveDirection.RIGHT;
    }

    @Test
    public void isHorizonal() {
        assertFalse(up.isHorizonal());
        assertFalse(down.isHorizonal());
        assertTrue(left.isHorizonal());
        assertTrue(right.isHorizonal());
    }

    @Test
    public void getOpposite() {
        assertEquals(up, MoveDirection.getOpposite(down));
        assertEquals(down, MoveDirection.getOpposite(up));
        assertEquals(left, MoveDirection.getOpposite(right));
        assertEquals(right, MoveDirection.getOpposite(left));
    }
}