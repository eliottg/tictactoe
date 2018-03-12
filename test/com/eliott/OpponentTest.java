package com.eliott;

import junit.framework.TestCase;

/**
 * Created by egray on 2/20/2018.
 */
public class OpponentTest extends TestCase {

    public void testSetDifficulty() {
        Opponent opponent = new Opponent("X");
        opponent.setDifficulty(1);
    }

    public void testGetWinningEasyMove() {
        Board board = new Board();
        Opponent opponent = new Opponent("X");
        board.setBoardMatrix(new String[][]{
                {"X", "", ""},
                {"", "", ""},
                {"X", "", ""}});
        Move winningMove = opponent.getEasyMove(board, "X");
        assertEquals(1, winningMove.getRow());
        assertEquals(0, winningMove.getCol());
        assertEquals("X", winningMove.getToken());
        assertTrue(winningMove.isWinningMove());
    }

    public void testGetEasyMoveInEmptyBoard() {
        Board board = new Board();
        Opponent opponent = new Opponent("X");
        Move move = opponent.getEasyMove(board, "X");
        assertEquals("X", move.getToken());
        assertNotNull(move.getRow());
        assertNotNull(move.getCol());
        assertFalse(move.isWinningMove());
    }

    public void testGetRandomMove_lastAvailableMove() {
        Board board = new Board();
        Opponent opponent = new Opponent("X");
        board.setBoardMatrix(new String[][]{
                {"X", "", "X"},
                {"X", "X", "X"},
                {"X", "X", "X"}});
        Move move = opponent.getRandomMove(board, "X");
        assertEquals("X", move.getToken());
        assertEquals(0, move.getRow());
        assertEquals(1, move.getCol());
    }

    public void testGetRandomMove_emptyBoard(){
        Board board = new Board();
        Opponent opponent = new Opponent("X");
        Move move = opponent.getRandomMove(board, "X");
        assertEquals("X", move.getToken());
        assertNotNull(move.getRow());
        assertNotNull(move.getCol());
    }

}
