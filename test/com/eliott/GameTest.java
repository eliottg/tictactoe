package com.eliott;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by egray on 2/20/2018.
 */
public class GameTest {

    private Game game;

    @Before
    public void setUp() {
        game = new Game(1, "X", "O");
    }

    @Test
    public void testMoveIsValid_false() {
        game.makeManualMove(0, 0);
        assertFalse(game.moveIsValid(0, 0));
    }

    @Test
    public void testMoveIsValid_true() {
        game.makeManualMove(0, 0);
        assertTrue(game.moveIsValid(1, 1));
    }

    @Test
    public void testMakeComputerMove_easy() {
        game.makeComputerMove();
        int movesRemainingCount = game.board.getAvailableMovesList().size();
        assertEquals(8, movesRemainingCount);
    }

    @Test
    public void testMakeComputerMove_hard() {
        game = new Game(1, "X", "O");
        game.makeComputerMove();
        int movesRemainingCount = game.board.getAvailableMovesList().size();
        assertEquals(8, movesRemainingCount);
    }

    @Test
    public void testMakeManualMove_valid() {
        boolean success = game.makeManualMove(0, 0);
        assertEquals(8, game.board.getAvailableMovesList().size());
        assertEquals("X", game.board.getBoardMatrix()[0][0].getToken());
        assertTrue(success);
    }

    @Test
    public void testMakeManualMove_invalid() {
        game.board.setBoardMatrix(new String[][]{
                {"X", "X", "X"},
                {"X", "X", "X"},
                {"X", "X", "X"}});
        boolean failure = game.makeManualMove(0, 0);
        assertFalse(failure);
    }

    @Test
    public void testMakeEasyMove_WinningMove() {
        game.board.setBoardMatrix(new String[][]{
                {"O", " ", " "},
                {" ", " ", " "},
                {"O", " ", " "}});
        game.makeEasyMove();
        Cell winningCell = game.board.getBoardMatrix()[1][0];
        assertEquals(1, winningCell.getRow());
        assertEquals(0, winningCell.getCol());
        assertEquals("O", winningCell.getToken());
    }

    @Test
    public void testMakeEasyMove_InEmptyBoard() {
        game.makeEasyMove();
        assertEquals(8, game.board.getAvailableMovesList().size());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMakeEasyMove_InFullBoard() {
        game.board.setBoardMatrix(new String[][]{
                {"X", "X", "X"},
                {"X", "X", "X"},
                {"X", "X", "X"}});
        game.makeEasyMove();
    }

    @Test
    public void testGetHardMove() {
        game.makeHardMove();
        assertEquals(8, game.board.getAvailableMovesList().size());

    }

    @Test
    public void testMakeImpossibleMove() {
        game.makeImpossibleMove();
        assertEquals(8, game.board.getAvailableMovesList().size());

    }

}
