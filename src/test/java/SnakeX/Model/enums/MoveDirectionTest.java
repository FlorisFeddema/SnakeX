package SnakeX.Model.enums;

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
        up = MoveDirection.Up;
        down = MoveDirection.Down;
        left = MoveDirection.Left;
        right = MoveDirection.Right;
    }

    @Test
    public void isHorizonal() {
        assertFalse(up.isHorizonal());
        assertFalse(down.isHorizonal());
        assertTrue(left.isHorizonal());
        assertTrue(right.isHorizonal());
    }
}