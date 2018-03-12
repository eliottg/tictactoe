package com.eliott;

import junit.framework.TestCase;

/**
 * Created by egray on 2/21/2018.
 */
public class MoveTest extends TestCase {

    public void testGetRow() {
        Move move = new Move(0, 1, "X", false);
        assertEquals(0, move.getRow());
    }

    public void testGetCol() {
        Move move = new Move(0, 1, "X", false);
        assertEquals(1, move.getCol());
    }

    public void testGetToken() {
        Move move = new Move(0, 1, "X", false);
        assertEquals("X", move.getToken());
    }

    public void testGetWinningFlag() {
        Move move = new Move(0, 1, "X", false);
        assertFalse(move.isWinningMove());
    }
}