package com.eliott;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * Created by egray on 2/20/2018.
 */


public class BoardTest{

    @Test
    public void testNewBoardCellsAreNull() {
        Board board = new Board();
        String[][] boardMatrix = board.getBoardMatrix();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(" ", boardMatrix[i][j]);
            }
        }
    }

    @Test
    public void testSetBoardmatrix() {
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"X", "O", "X"},
                {"O", "X", "O"},
                {"X", "O", "X"}});
        assertEquals("X", board.getBoardMatrix()[0][0]);
        assertEquals("O", board.getBoardMatrix()[1][0]);
        assertEquals("X", board.getBoardMatrix()[2][0]);
    }

    @Test
    public void testMoveSuccess() {
        Board board = new Board();
        Move move = new Move(0, 0, "X");
        board.addMove(move);
        assertEquals(board.getBoardMatrix()[0][0], "X");
    }

    @Test
    public void testMoveIsInvalid() {
        Board board = new Board();
        Move firstMove = new Move(0, 0, "X");
        Move secondMove = new Move(0, 0, "O");
        board.addMove(firstMove);
        assertFalse(board.moveIsValid(secondMove));
    }

    @Test
    public void testMoveIsValid() {
        Board board = new Board();
        Move firstMove = new Move(0, 0, "X");
        Move secondMove = new Move(1, 0, "O");
        board.addMove(firstMove);
        assertTrue(board.moveIsValid(secondMove));
    }

    @Test
    public void testGetListOfAvailableMoves_almostFullBoard(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"X", " ", "X"},
                {"X", "X", "X"},
                {"X", "X", "X"}});
        assertEquals(1, board.getListOfAvailableMoves().size());
    }

    @Test
    public void testGetListOfAvailableMoves_emptyBoard(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        assertEquals(9, board.getListOfAvailableMoves().size());
    }

    @Test
    public void testGetGameState_Ongoing() {
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"X", " ", "X"},
                {"", " ", " "},
                {" ", "X", " "}});
        assertEquals("Ongoing", board.getGameState());
    }

    @Test
    public void testGetGameState_Win() {
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"X", "X", "X"},
                {" ", " ", " "},
                {" ", " ", " "}});
        assertEquals("Win", board.getGameState());
    }

    @Test
    public void testGetGameState_Tied() {
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"O", "X", "X"},
                {"X", "O", "O"},
                {"X", "O", "X"}});
        assertEquals("Tie", board.getGameState());
    }

    @Test
    public void testIsVictory_true(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"X", "X", "X"},
                {"", "", ""},
                {"", "", ""}});
        assertTrue(board.isVictory());
    }

    @Test
    public void testIsRowVictory_false(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"X", " ", "X"},
                {" ", " ", " "},
                {" ", " ", " "}});
        assertFalse(board.isVictory());
    }

    @Test
    public void testIsColumnVictory_true(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"X", " ", " "},
                {"X", " ", " "},
                {"X", " ", " "}});
        assertTrue(board.isVictory());
    }

    @Test
    public void testIsColumnVictory_false(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", "X", " "},
                {" ", "X", " "}});
        assertFalse(board.isVictory());
    }

    @Test
    public void testIsDiagonalVictory_forward(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"X", " ", " "},
                {" ", "X", " "},
                {" ", " ", "X"}});
        assertTrue(board.isVictory());
    }

    @Test
    public void testIsDiagonalVictory_backward(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {" ", " ", "X"},
                {" ", "X", " "},
                {"X", " ", " "}});
        assertTrue(board.isVictory());
    }

    @Test
    public void testIsDiagonalVictory_false(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {" ", " ", "X"},
                {" ", "X", " "},
                {" ", " ", " "}});
        assertFalse(board.isVictory());
    }

    @Test
    public void testCheckRowForVictory_true(){
        Board board = new Board();
        String[] row = new String[]{"X", "X", "X"};
        assertTrue(board.checkRowForVictory(row));
    }

    @Test
    public void testCheckRowForVictory_false(){
        Board board = new Board();
        String[] row = new String[]{"X", "O", "X"};
        assertFalse(board.checkRowForVictory(row));
    }

    @Test
    public void testBoardIsFull_fullBoard(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"O", "X", "X"},
                {"X", "O", "O"},
                {"X", "O", "X"}});
        assertTrue(board.boardIsFull());
    }

    @Test
    public void testBoardIsFull_notFullBoard(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"O", "X", "X"},
                {"X", "O", "O"},
                {"X", "O", "X"}});
        assertTrue(board.boardIsFull());
    }

    @Test
    public void testSearchBoardForWinningMove_withPossibleRowWin(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"X", "X", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        Move winningMove = board.searchBoardForWinningMove("X");
        assertEquals(0, winningMove.getRow());
        assertEquals(2, winningMove.getCol());
        assertEquals("X", winningMove.getToken());
    }

    @Test
    public void testSearchBoardForWinningMove_withPossibleColumnWin(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"X", " ", " "},
                {"X", " ", " "},
                {" ", " ", " "}});
        Move winningMove = board.searchBoardForWinningMove("X");
        assertEquals(2, winningMove.getRow());
        assertEquals(0, winningMove.getCol());
        assertEquals("X", winningMove.getToken());
    }

    @Test
    public void testSearchBoardForWinningMove_withPossibleForwardDiagonalWin(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"X", " ", " "},
                {" ", "X", " "},
                {" ", "", " "}});
        Move winningMove = board.searchBoardForWinningMove("X");
        assertEquals(2, winningMove.getRow());
        assertEquals(2, winningMove.getCol());
        assertEquals("X", winningMove.getToken());
    }

    @Test
    public void testSearchBoardForWinningMove_withPossibleBackwardDiagonalWin(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {" ", " ", "X"},
                {" ", " ", " "},
                {"X", " ", " "}});
        Move winningMove = board.searchBoardForWinningMove("X");
        assertEquals(1, winningMove.getRow());
        assertEquals(1, winningMove.getCol());
        assertEquals("X", winningMove.getToken());
    }

    @Test
    public void testGetWinningCoords_failure(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {" ", " ", "X"},
                {" ", " ", ""},
                {"X", " ", " "}});
        Move winningMove = board.searchBoardForWinningMove("O");
        assertNull(winningMove);
    }

    @Test
    public void testFindThreeInARow_success(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"X", "X", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        int[][] rowCoordinates = new int[][]{{0, 0}, {0, 1}, {0, 2}};
        Move move = board.findTwoInARow("X", rowCoordinates);
        assertEquals(0, move.getRow());
        assertEquals(2, move.getCol());
        assertEquals("X", move.getToken());
    }

    @Test
    public void testFindThreeInARow_failureWithBothTokens(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"X", "X", "O"},
                {" ", " ", " "},
                {" ", " ", " "}});
        int[][] rowCoordinates = new int[][]{{0, 0}, {0, 1}, {0, 2}};
        Move move = board.findTwoInARow("X", rowCoordinates);
        assertNull(move);
    }

    @Test
    public void testFindThreeInARow_failure(){
        Board board = new Board();
        board.setBoardMatrix(new String[][]{
                {"O", "O", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        int[][] rowCoordinates = new int[][]{{0, 0}, {0, 1}, {0, 2}};
        Move move = board.findTwoInARow("X", rowCoordinates);
        assertNull(move);
    }
}
