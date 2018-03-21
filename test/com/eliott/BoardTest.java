package com.eliott;

import static org.junit.Assert.*;
import org.junit.*;

/**
 * Created by egray on 2/20/2018.
 */


public class BoardTest{

    private Board board;

    @Before public void setUp() {
        board = new Board();
    }
    
    @Test
    public void testNewBoardCellsAreNull() {
        String[][] boardMatrix = board.getBoardMatrix();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(" ", boardMatrix[i][j]);
            }
        }
    }

    @Test
    public void testSetBoardmatrix() {
        board.setBoardMatrix(new String[][]{
                {"X", "O", "X"},
                {"O", "X", "O"},
                {"X", "O", "X"}});
        assertEquals("X", board.getBoardMatrix()[0][0]);
        assertEquals("O", board.getBoardMatrix()[1][0]);
        assertEquals("X", board.getBoardMatrix()[2][0]);
    }

    @Test
    public void testAddMove_MoveSuccess() {
        Move move = new Move(0, 0, "X");
        board.addMove(move);
        assertEquals(board.getBoardMatrix()[0][0], "X");
    }

    @Test
    public void testMoveIsValid_false() {
        Move firstMove = new Move(0, 0, "X");
        Move secondMove = new Move(0, 0, "O");
        board.addMove(firstMove);
        assertFalse(board.moveIsValid(secondMove));
    }

    @Test
    public void testMoveIsValid_true() {
        Move firstMove = new Move(0, 0, "X");
        Move secondMove = new Move(1, 0, "O");
        board.addMove(firstMove);
        assertTrue(board.moveIsValid(secondMove));
    }

    @Test
    public void testGetListOfAvailableMoves_almostFullBoard(){
        board.setBoardMatrix(new String[][]{
                {"X", " ", "X"},
                {"X", "X", "X"},
                {"X", "X", "X"}});
        assertEquals(1, board.getListOfAvailableMoves().size());
    }

    @Test
    public void testGetListOfAvailableMoves_emptyBoard(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        assertEquals(9, board.getListOfAvailableMoves().size());
    }

    @Test
    public void testGetGameState_Ongoing() {
        board.setBoardMatrix(new String[][]{
                {"X", " ", "X"},
                {"", " ", " "},
                {" ", "X", " "}});
        assertEquals("Ongoing", board.getGameState());
    }

    @Test
    public void testGetGameState_Win() {
        board.setBoardMatrix(new String[][]{
                {"X", "X", "X"},
                {" ", " ", " "},
                {" ", " ", " "}});
        assertEquals("Win", board.getGameState());
    }

    @Test
    public void testGetGameState_Tied() {
        board.setBoardMatrix(new String[][]{
                {"O", "X", "X"},
                {"X", "O", "O"},
                {"X", "O", "X"}});
        assertEquals("Tie", board.getGameState());
    }

    @Test
    public void testIsVictory_true(){
        board.setBoardMatrix(new String[][]{
                {"X", "X", "X"},
                {"", "", ""},
                {"", "", ""}});
        assertTrue(board.isVictory());
    }

    @Test
    public void testIsRowVictory_false(){
        board.setBoardMatrix(new String[][]{
                {"X", " ", "X"},
                {" ", " ", " "},
                {" ", " ", " "}});
        assertFalse(board.isVictory());
    }

    @Test
    public void testIsColumnVictory_true(){
        board.setBoardMatrix(new String[][]{
                {"X", " ", " "},
                {"X", " ", " "},
                {"X", " ", " "}});
        assertTrue(board.isVictory());
    }

    @Test
    public void testIsColumnVictory_false(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", "X", " "},
                {" ", "X", " "}});
        assertFalse(board.isVictory());
    }

    @Test
    public void testIsDiagonalVictory_forward(){
        board.setBoardMatrix(new String[][]{
                {"X", " ", " "},
                {" ", "X", " "},
                {" ", " ", "X"}});
        assertTrue(board.isVictory());
    }

    @Test
    public void testIsDiagonalVictory_backward(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", "X"},
                {" ", "X", " "},
                {"X", " ", " "}});
        assertTrue(board.isVictory());
    }

    @Test
    public void testIsDiagonalVictory_false(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", "X"},
                {" ", "X", " "},
                {" ", " ", " "}});
        assertFalse(board.isVictory());
    }

    @Test
    public void testCheckRowForVictory_true(){
        String[] row = new String[]{"X", "X", "X"};
        assertTrue(board.checkRowForVictory(row));
    }

    @Test
    public void testCheckRowForVictory_false(){
        String[] row = new String[]{"X", "O", "X"};
        assertFalse(board.checkRowForVictory(row));
    }

    @Test
    public void testBoardIsFull_fullBoard(){
        board.setBoardMatrix(new String[][]{
                {"O", "X", "X"},
                {"X", "O", "O"},
                {"X", "O", "X"}});
        assertTrue(board.boardIsFull());
    }

    @Test
    public void testBoardIsFull_notFullBoard(){
        board.setBoardMatrix(new String[][]{
                {"O", "X", "X"},
                {"X", "O", "O"},
                {"X", "O", "X"}});
        assertTrue(board.boardIsFull());
    }

    @Test
    public void testSearchBoardForWinningMove_withPossibleRowWin(){
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
        board.setBoardMatrix(new String[][]{
                {" ", " ", "X"},
                {" ", " ", ""},
                {"X", " ", " "}});
        Move winningMove = board.searchBoardForWinningMove("O");
        assertNull(winningMove);
    }

    @Test
    public void testFindThreeInARow_success(){
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
        board.setBoardMatrix(new String[][]{
                {"O", "O", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        int[][] rowCoordinates = new int[][]{{0, 0}, {0, 1}, {0, 2}};
        Move move = board.findTwoInARow("X", rowCoordinates);
        assertNull(move);
    }
}
