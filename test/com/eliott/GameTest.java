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
        Move firstMove = new Move(0, 0, "X");
        Move secondMove = new Move(0, 0, "O");
        game.makeManualMove(firstMove);
        assertFalse(game.moveIsValid(secondMove));
    }

    @Test
    public void testMoveIsValid_true() {
        Move firstMove = new Move(0, 0, "X");
        Move secondMove = new Move(1, 0, "O");
        game.makeManualMove(firstMove);
        assertTrue(game.moveIsValid(secondMove));
    }

    @Test
    public void testMakeComputerMove_easy() {
        game.makeComputerMove();
        int movesRemainingCount = game.board.getListOfAvailableMoves().size();
        assertEquals(8, movesRemainingCount);
    }

    @Test
    public void testMakeComputerMove_hard() {
        game = new Game(1, "X", "O");
        game.makeComputerMove();
        int movesRemainingCount = game.board.getListOfAvailableMoves().size();
        assertEquals(8, movesRemainingCount);
    }

    @Test
    public void testMakeManualMove_valid() {
        game.makeManualMove(new Move(0, 0, "X"));
        String[][] expectedBoardMatrix = new String[][]{
                {"X", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}};
        assertArrayEquals(expectedBoardMatrix,
                game.board.getBoardMatrix());
    }

    @Test(expected = IllegalStateException.class)
    public void testMakeManualMove_invalid() {
        game.board.setBoardMatrix(new String[][]{
                {"X", "X", "X"},
                {"X", "X", "X"},
                {"X", "X", "X"}});
        game.makeManualMove(new Move(0, 0, "X"));
    }

    @Test
    public void testGetEasyMove_WinningMove() {
        game.makeManualMove(new Move(0, 0, "O"));
        game.makeManualMove(new Move(2, 0, "O"));
        Move winningMove = game.getEasyMove();
        assertEquals(1, winningMove.getRow());
        assertEquals(0, winningMove.getCol());
        assertEquals("O", winningMove.getToken());
    }

    @Test
    public void testGetEasyMove_InEmptyBoard() {
        Move move = game.getEasyMove();
        assertEquals("O", move.getToken());
        assertNotNull(move.getRow());
        assertNotNull(move.getCol());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetEasyMove_InFullBoard() {
        game.board.setBoardMatrix(new String[][]{
                {"X", "X", "X"},
                {"X", "X", "X"},
                {"X", "X", "X"}});
        Move move = game.getEasyMove();
        assertEquals("X", move.getToken());
        assertNotNull(move.getRow());
        assertNotNull(move.getCol());
    }

    @Test
    public void testGetRandomMove_lastAvailableMove() {
        game.board.setBoardMatrix(new String[][]{
                {"X", " ", "X"},
                {"X", "X", "X"},
                {"X", "X", "X"}});
        Move move = game.getRandomMove();
        assertEquals("O", move.getToken());
        assertEquals(0, move.getRow());
        assertEquals(1, move.getCol());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testGetRandomMove_noEmptySpaces(){
        game.board.setBoardMatrix(new String[][]{
                {"X", "X", "X"},
                {"X", "X", "X"},
                {"X", "X", "X"}});
        Move move = game.getRandomMove();
        assertEquals("X", move.getToken());
        assertEquals(0, move.getRow());
        assertEquals(1, move.getCol());
    }

    @Test
    public void testGetHardMove() {
        Move move = game.getHardMove();
        assertEquals("O", move.getToken());
        assertNotNull(move.getRow());
        assertNotNull(move.getCol());
    }

    @Test
    public void testGetImpossibleMove() {
        Move move = game.getImpossibleMove();
        assertEquals("O", move.getToken());
        assertNotNull(move.getRow());
        assertNotNull(move.getCol());
    }

}
