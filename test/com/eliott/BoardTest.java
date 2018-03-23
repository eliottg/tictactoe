package com.eliott;

import static org.junit.Assert.*;
import org.junit.*;

import java.util.HashSet;

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
    public void testGetWinningMoveSet_withSingleWinningMove(){
        board.setBoardMatrix(new String[][]{
                {" ", "X", "X"},
                {" ", " ", "O"},
                {" ", " ", "X"}});
        HashSet<Move> winningMoveSet = board.getWinningMovesSet("X");
        assertEquals(1, winningMoveSet.size());
    }

    @Test
    public void testGetWinningMoveSet_withTwoWinningMoves(){
        board.setBoardMatrix(new String[][]{
                {" ", "X", "X"},
                {" ", "O", "X"},
                {" ", " ", " "}});
        HashSet<Move> winningMoveSet = board.getWinningMovesSet("X");
        assertEquals(2, winningMoveSet.size());
    }

    @Test
    public void testGetWinningMoveSet_withThreeWinningMoves(){
        board.setBoardMatrix(new String[][]{
                {" ", "X", "X"},
                {" ", "X", "O"},
                {" ", " ", " "}});
        HashSet<Move> winningMoveSet = board.getWinningMovesSet("X");
        assertEquals(3, winningMoveSet.size());
    }

    @Test
    public void testGetWinningMove_withPossibleRowWin(){
        board.setBoardMatrix(new String[][]{
                {"X", "X", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        Move winningMove = board.getWinningMove("X");
        assertEquals(0, winningMove.getRow());
        assertEquals(2, winningMove.getCol());
        assertEquals("X", winningMove.getToken());
    }

    @Test
    public void testGetWinningMove_withPossibleColumnWin(){
        board.setBoardMatrix(new String[][]{
                {"X", " ", " "},
                {"X", " ", " "},
                {" ", " ", " "}});
        Move winningMove = board.getWinningMove("X");
        assertEquals(2, winningMove.getRow());
        assertEquals(0, winningMove.getCol());
        assertEquals("X", winningMove.getToken());
    }

    @Test
    public void testGetWinningMove_withPossibleForwardDiagonalWin(){
        board.setBoardMatrix(new String[][]{
                {"X", " ", " "},
                {" ", "X", " "},
                {" ", "", " "}});
        Move winningMove = board.getWinningMove("X");
        assertEquals(2, winningMove.getRow());
        assertEquals(2, winningMove.getCol());
        assertEquals("X", winningMove.getToken());
    }

    @Test
    public void testGetWinningMove_withPossibleBackwardDiagonalWin(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", "X"},
                {" ", " ", " "},
                {"X", " ", " "}});
        Move winningMove = board.getWinningMove("X");
        assertEquals(1, winningMove.getRow());
        assertEquals(1, winningMove.getCol());
        assertEquals("X", winningMove.getToken());
    }

    @Test
    public void testGetWinningMove_failure(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", "O"},
                {" ", " ", ""},
                {"O", " ", " "}});
        Move winningMove = board.getWinningMove("X");
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

    @Test
    public void testFindForkOpportunity_failure(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        Move move = board.findForkOpportunity("X");
        assertNull(move);
    }

    @Test
    public void testFindForkOpportunity_success(){
        board.setBoardMatrix(new String[][]{
                {" ", "X", " "},
                {"X", " ", " "},
                {" ", " ", " "}});
        Move move = board.findForkOpportunity("X");
        int row = move.getRow();
        int col = move.getCol();
        assertTrue((row == 0 && col == 0) || (row == 1 && col == 1));
        assertNotNull(move);
    }

    @Test
    public void testFindCenterOpportunity_failure(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", "X", " "},
                {" ", " ", " "}});
        Move move = board.findCenterOpportunity("X");
        assertNull(move);
    }

    @Test
    public void testFindCenterOpportunity_success(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        Move move = board.findCenterOpportunity("X");
        assertEquals(1, move.getRow());
        assertEquals(1, move.getCol());
        assertEquals("X", move.getToken());
    }

    @Test
    public void testFindCornerOpportunity_failure() {
        board.setBoardMatrix(new String[][]{
                {"O", " ", "O"},
                {" ", " ", " "},
                {"O", " ", "O"}});
        Move move = board.findCornerOpportunity("X", "O");
        assertNull(move);
    }

    @Test
    public void testFindCornerOpportunity_oppositeUpperLeft() {
        board.setBoardMatrix(new String[][]{
                {"O", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        Move move = board.findCornerOpportunity("X", "O");
        assertEquals(2, move.getRow());
        assertEquals(2, move.getCol());
        assertEquals("X", move.getToken());
    }

    @Test
    public void testFindCornerOpportunity_oppositeBottomRight() {
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", " ", " "},
                {" ", " ", "X"}});
        Move move = board.findCornerOpportunity("O", "X");
        assertEquals(0, move.getRow());
        assertEquals(0, move.getCol());
        assertEquals("O", move.getToken());
    }

    @Test
    public void testFindCornerOpportunity_cpuAndPlayerEachHaveCorner() {
        board.setBoardMatrix(new String[][]{
                {" ", " ", "X"},
                {" ", " ", " "},
                {" ", " ", "O"}});
        Move move = board.findCornerOpportunity("O", "X");
        assertEquals(2, move.getRow());
        assertEquals(0, move.getCol());
        assertEquals("O", move.getToken());
    }

    @Test
    public void testFindCornerOpportunity_allEmpty() {
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        Move move = board.findCornerOpportunity("O", "X");
        int row = move.getRow();
        int col = move.getCol();
        assertTrue(row == 0 || row == 2);
        assertTrue(col == 0 || col == 2);
        assertEquals("O", move.getToken());
    }

    @Test
    public void testFindEmptySide_failure(){
        board.setBoardMatrix(new String[][]{
                {" ", "O", " "},
                {"O", " ", "X"},
                {" ", "X", " "}});
        Move move = board.findEdgeOpportunity("O");
        assertNull(move);
    }

    @Test
    public void testFindEmptySide_successRightSide(){
        board.setBoardMatrix(new String[][]{
                {" ", "O", " "},
                {"O", " ", " "},
                {" ", "X", " "}});
        Move move = board.findEdgeOpportunity("O");
        assertEquals(1, move.getRow());
        assertEquals(2, move.getCol());
        assertEquals("O", move.getToken());
    }

}
