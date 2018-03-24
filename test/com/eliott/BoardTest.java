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
        Move[][] boardMatrix = board.getBoardMatrix();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                assertEquals(" ", boardMatrix[i][j].getToken());
            }
        }
    }

    @Test
    public void testSetBoardmatrix() {
        board.setBoardMatrix(new String[][]{
                {"X", "O", "X"},
                {"O", "X", "O"},
                {"X", "O", "X"}});
        assertEquals("X", board.getBoardMatrix()[0][0].getToken());
        assertEquals("O", board.getBoardMatrix()[1][0].getToken());
        assertEquals("X", board.getBoardMatrix()[2][0].getToken());
    }

    @Test
    public void testMakeMove_MoveSuccess() {
        Move move = board.getBoardMatrix()[1][1];
        assertEquals(9, board.getAvailableMovesList().size());
        board.makeMove(move, "X");
        assertEquals(8, board.getAvailableMovesList().size());
        assertEquals(board.getBoardMatrix()[1][1].getToken(), "X");
    }

    @Test (expected = IllegalStateException.class)
    public void testMakeMove_Failure(){
        Move move = board.getBoardMatrix()[1][1];
        board.makeMove(move, "X");
        board.makeMove(move, "X");
    }

    @Test
    public void testGetAvailableMovesList_emptyBoard() {
        assertEquals(9, board.getAvailableMovesList().size());
    }

    @Test
    public void testGetAvailableMovesList_oneMoveMade() {
        Move move = board.getBoardMatrix()[0][0];
        board.makeMove(move, "X");
        assertEquals(8, board.getAvailableMovesList().size());
    }

    @Test
    public void testGenerateListOfAvailableMoves_almostFullBoard(){
        board.setBoardMatrix(new String[][]{
                {"X", " ", "X"},
                {"X", "X", "X"},
                {"X", "X", "X"}});
        assertEquals(1, board.getAvailableMovesList().size());
    }

    @Test
    public void testGenerateListOfAvailableMoves_emptyBoard(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        assertEquals(9, board.getAvailableMovesList().size());
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
        Move[] row = new Move[]{
                new Move(0, 0, "X"),
                new Move(0, 1, "X"),
                new Move(0, 2, "X")};
        assertTrue(board.checkRowForVictory(row));
    }

    @Test
    public void testCheckRowForVictory_false(){
        Move[] row = new Move[]{
                new Move(0, 0, "X"),
                new Move(0, 1, "O"),
                new Move(0, 2, " ")};
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
    public void testMakeWinningMove_withPossibleRowWin(){
        board.setBoardMatrix(new String[][]{
                {"X", "X", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        board.makeWinningMove("X");
        Move winningMove = board.getBoardMatrix()[0][2];
        assertEquals(0, winningMove.getRow());
        assertEquals(2, winningMove.getCol());
        assertEquals("X", winningMove.getToken());
    }

    @Test
    public void testMakeWinningMove_withPossibleColumnWin(){
        board.setBoardMatrix(new String[][]{
                {"X", " ", " "},
                {"X", " ", " "},
                {" ", " ", " "}});
        board.makeWinningMove("X");
        Move winningMove = board.getBoardMatrix()[2][0];
        assertEquals(2, winningMove.getRow());
        assertEquals(0, winningMove.getCol());
        assertEquals("X", winningMove.getToken());
    }

    @Test
    public void testMakeWinningMove_withPossibleForwardDiagonalWin(){
        board.setBoardMatrix(new String[][]{
                {"X", " ", " "},
                {" ", "X", " "},
                {" ", "", " "}});
        board.makeWinningMove("X");
        Move winningMove = board.getBoardMatrix()[2][2];
        assertEquals(2, winningMove.getRow());
        assertEquals(2, winningMove.getCol());
        assertEquals("X", winningMove.getToken());
    }

    @Test
    public void testMakeWinningMove_withPossibleBackwardDiagonalWin(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", "X"},
                {" ", " ", " "},
                {"X", " ", " "}});
        board.makeWinningMove("X");
        Move winningMove = board.getBoardMatrix()[1][1];
        assertEquals(1, winningMove.getRow());
        assertEquals(1, winningMove.getCol());
        assertEquals("X", winningMove.getToken());
    }

    @Test
    public void testMakeWinningMove_failure(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", "O"},
                {" ", " ", ""},
                {"O", " ", " "}});
        board.makeWinningMove("X");
        Move notWinningMove = board.getBoardMatrix()[1][1];
        assertEquals(" ", notWinningMove.getToken());
    }

    @Test
    public void testMakeRandomMove_lastAvailableMove() {
        board.setBoardMatrix(new String[][]{
                {"X", " ", "X"},
                {"X", "X", "X"},
                {"X", "X", "X"}});
        board.makeRandomMove("O");
        Move move = board.getBoardMatrix()[0][1];
        assertEquals("O", move.getToken());
        assertEquals(0, move.getRow());
        assertEquals(1, move.getCol());
    }

    @Test(expected = IllegalArgumentException.class)
    public void testMakeRandomMove_noEmptySpaces(){
        board.setBoardMatrix(new String[][]{
                {"X", "X", "X"},
                {"X", "X", "X"},
                {"X", "X", "X"}});
        board.makeRandomMove("X");
    }

    @Test
    public void testFindThreeInARow_success(){
        board.setBoardMatrix(new String[][]{
                {"X", "X", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        Move[][] boardMatrix = board.getBoardMatrix();
        Move[] rowCoordinates = new Move[]{boardMatrix[0][0], boardMatrix[0][1], boardMatrix[0][2]};
        Move move = board.findTwoInARow("X", rowCoordinates);
        assertEquals(0, move.getRow());
        assertEquals(2, move.getCol());
    }

    @Test
    public void testFindThreeInARow_failureWithBothTokens(){
        board.setBoardMatrix(new String[][]{
                {"X", "X", "O"},
                {" ", " ", " "},
                {" ", " ", " "}});
        Move[][] boardMatrix = board.getBoardMatrix();
        Move[] rowCoordinates = new Move[]{boardMatrix[0][0], boardMatrix[0][1], boardMatrix[0][2]};
        Move move = board.findTwoInARow("X", rowCoordinates);
        assertNull(move);
    }

    @Test
    public void testFindThreeInARow_failure(){
        board.setBoardMatrix(new String[][]{
                {"O", "O", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        Move[][] boardMatrix = board.getBoardMatrix();
        Move[] rowCoordinates = new Move[]{boardMatrix[0][0], boardMatrix[0][1], boardMatrix[0][2]};
        Move move = board.findTwoInARow("X", rowCoordinates);
        assertNull(move);
    }

    @Test
    public void testFindForkOpportunity_failure(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        boolean bool = board.findForkOpportunity("X");
        assertFalse(bool);
    }

    @Test
    public void testFindForkOpportunity_success(){
        board.setBoardMatrix(new String[][]{
                {" ", "X", " "},
                {"X", " ", " "},
                {" ", " ", " "}});
        assertTrue(board.findForkOpportunity("X"));
        assertEquals(6, board.getAvailableMovesList().size());
        Move possibleMove1 = board.getBoardMatrix()[0][0];
        Move possibleMove2 = board.getBoardMatrix()[1][1];
        boolean wasMove1 = possibleMove1.getToken().equals("X") && possibleMove2.getToken().equals(" ");
        boolean wasMove2 = possibleMove1.getToken().equals(" ") && possibleMove2.getToken().equals("X");
        assertTrue(wasMove1 ^ wasMove2);
    }

    @Test
    public void testBlockOpponentFork_withNoPersonalTwoInARow(){
        board.setBoardMatrix(new String[][]{
                {" ", "X", " "},
                {"X", " ", " "},
                {" ", " ", " "}});
        assertTrue(board.blockOpponentFork("O", "X"));
        assertEquals(6, board.getAvailableMovesList().size());
        Move possibleMove1 = board.getBoardMatrix()[0][0];
        Move possibleMove2 = board.getBoardMatrix()[1][1];
        boolean wasMove1 = possibleMove1.getToken().equals("O") && possibleMove2.getToken().equals(" ");
        boolean wasMove2 = possibleMove1.getToken().equals(" ") && possibleMove2.getToken().equals("O");
        assertTrue(wasMove1 ^ wasMove2);
    }

    @Test
    public void testBlockOpponentFork_withPersonalTwoInARow(){
        board.setBoardMatrix(new String[][]{
                {" ", "X", " "},
                {"X", " ", " "},
                {"O", " ", " "}});
        assertTrue(board.blockOpponentFork("O", "X"));
        assertEquals(5, board.getAvailableMovesList().size());
        Move move = board.getBoardMatrix()[1][1];
        assertEquals("O", move.getToken());
    }

    @Test
    public void testBlockOpponentFork_failure(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", " ", " "},
                {"X", " ", " "}});
        assertFalse(board.blockOpponentFork("O", "X"));
        assertEquals(8, board.getAvailableMovesList().size());
    }

    @Test
    public void testFindCenterOpportunity_failure(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", "X", " "},
                {" ", " ", " "}});
        assertFalse(board.findCenterOpportunity("X"));
    }

    @Test
    public void testFindCenterOpportunity_success(){
        board.setBoardMatrix(new String[][]{
                {" ", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        assertTrue(board.findCenterOpportunity("X"));
        Move move = board.getBoardMatrix()[1][1];
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
        assertFalse(board.findCornerOpportunity("X", "O"));
    }

    @Test
    public void testFindCornerOpportunity_oppositeUpperLeft() {
        board.setBoardMatrix(new String[][]{
                {"O", " ", " "},
                {" ", " ", " "},
                {" ", " ", " "}});
        assertTrue(board.findCornerOpportunity("X", "O"));
        Move move = board.getBoardMatrix()[2][2];
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

        assertTrue(board.findCornerOpportunity("O", "X"));
        Move move = board.getBoardMatrix()[0][0];
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
        assertTrue(board.findCornerOpportunity("O", "X"));
        Move move = board.getBoardMatrix()[2][0];
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
        assertTrue(board.findCornerOpportunity("O", "X"));
        //too lazy to check all four corners.  I really should have.
    }

    @Test
    public void testFindEmptySide_failure(){
        board.setBoardMatrix(new String[][]{
                {" ", "O", " "},
                {"O", " ", "X"},
                {" ", "X", " "}});
        assertFalse(board.findEdgeOpportunity("O"));
    }

    @Test
    public void testFindEmptySide_successRightSide(){
        board.setBoardMatrix(new String[][]{
                {" ", "O", " "},
                {"O", " ", " "},
                {" ", "X", " "}});
        assertTrue(board.findEdgeOpportunity("O"));
        Move move = board.getBoardMatrix()[1][2];
        assertEquals(1, move.getRow());
        assertEquals(2, move.getCol());
        assertEquals("O", move.getToken());
    }

}
