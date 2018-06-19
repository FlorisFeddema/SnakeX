package SnakeX.Model.Shared;

import SnakeX.Model.Shared.Point;
import org.junit.Before;
import org.junit.Test;


import static org.junit.Assert.*;

public class PointTest {

    Point point1;
    Point point2;
    Point point3;
    Point point4;

    @Before
    public void setUp() throws Exception {
        point1 = new Point(5, 5);
        point2 = new Point(2, 6);
        point3 = new Point(20, 6);
        point4 = new Point(2, -1);
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

    @Test
    public void isOnGrid(){
        assertTrue(point1.isOnGrid());
        assertTrue(point2.isOnGrid());
        assertFalse(point3.isOnGrid());
        assertFalse(point4.isOnGrid());
    }
}