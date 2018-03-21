package com.eliott;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by egray on 2/21/2018.
 */
public class MoveTest{

    private Move move;

    @Before
    public void setUp() {
        move = new Move(0, 1, "X");
    }

    @Test
    public void testGetRow() {
        assertEquals(0, move.getRow());
    }

    @Test
    public void testGetCol() {
        assertEquals(1, move.getCol());
    }

    @Test
    public void testGetToken() {
        assertEquals("X", move.getToken());
    }
}
