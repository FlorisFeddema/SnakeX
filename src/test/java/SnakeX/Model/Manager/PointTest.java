package SnakeX.Model.Manager;

import SnakeX.Model.Shared.Point;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class PointTest {

    Point point1;
    Point point2;

    @Before
    public void setUp() throws Exception {
        point1 = new Point(5, 5);
        point2 = new Point(2, 6);
    }

    @Test
    public void getX() {
        assertEquals(5, point1.getX());
        assertEquals(2, point2.getX());
    }

    @Test
    public void getY() {
        assertEquals(5, point1.getY());
        assertEquals(6, point2.getY());
    }
}