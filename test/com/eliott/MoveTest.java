package com.eliott;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by egray on 2/21/2018.
 */
public class MoveTest{

    @Test
    public void testGetRow() {
        Move move = new Move(0, 1, "X");
        assertEquals(0, move.getRow());
    }

    @Test
    public void testGetCol() {
        Move move = new Move(0, 1, "X");
        assertEquals(1, move.getCol());
    }

    @Test
    public void testGetToken() {
        Move move = new Move(0, 1, "X");
        assertEquals("X", move.getToken());
    }
}
