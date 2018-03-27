package com.eliott;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by egray on 2/20/2018.
 */
class Board {

    String noToken = " ";
    private Cell[][] boardMatrix = new Cell[3][3];
    private ArrayList<Cell> availableMovesList;

    Board() {
        availableMovesList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Cell newCell = new Cell(i, j, noToken);
                boardMatrix[i][j] = newCell;
                availableMovesList.add(newCell);

            }
        }
    }

    Cell[][] getBoardMatrix() { return boardMatrix; }

    ArrayList<Cell> getAvailableMovesList() { return availableMovesList; }

    void setBoardMatrix(String[][] newBoardMatrix) {
        availableMovesList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String newToken = newBoardMatrix[i][j];
                Cell existingCell = boardMatrix[i][j];
                existingCell.setToken(newToken);
                if(newToken.equals(noToken)){
                    availableMovesList.add(existingCell);
                }
            }
        }
    }

    void makeMove(Cell cell, String token) {
        if(!cell.getToken().equals(noToken)){
            throw new IllegalStateException("Invalid move made. Validity should be checked earlier.");
        }
        cell.setToken(token);
        availableMovesList.remove(cell);
    }

    String getGameState() {
        if (isVictory()) {
            return "Win";
        } else if (boardIsFull()) {
            return "Tie";
        } else {
            return "Ongoing";
        }
    }

    boolean isVictory() {
        for (int i = 0; i < 3; i++) { //todo prepopulate these with move objects
            Cell[] row = new Cell[]{boardMatrix[i][0], boardMatrix[i][1], boardMatrix[i][2]};
            if (checkRowForVictory(row)){
                return true;
            }

            Cell[] column = new Cell[]{boardMatrix[0][i], boardMatrix[1][i], boardMatrix[2][i]};
            if (checkRowForVictory(column)){
                return true;
            }
        }

        Cell[] frontDiagonal = new Cell[]{boardMatrix[0][0], boardMatrix[1][1], boardMatrix[2][2]};
        if (checkRowForVictory(frontDiagonal)){
            return true;
        }

        Cell[] backDiagonal = new Cell[]{boardMatrix[0][2], boardMatrix[1][1], boardMatrix[2][0]};
        if (checkRowForVictory(backDiagonal)){
            return true;
        }

        return false;
    }

    boolean checkRowForVictory(Cell[] row) {
        String cellOneToken = row[0].getToken();
        String cellTwoToken = row[1].getToken();
        String cellThreeToken = row[2].getToken();
        boolean rowIsIdentical = cellOneToken.equals(cellTwoToken) && cellTwoToken.equals(cellThreeToken);
        boolean rowIsNotEmpty = !cellOneToken.equals(noToken);
        return rowIsIdentical && rowIsNotEmpty;
    }

    boolean boardIsFull() {
        return availableMovesList.size() == 0;
    }

    HashSet<Cell> getWinningMovesSet(String token) {
        HashSet<Cell> cellSet = new HashSet<>();
        Cell cell;
        // check Rows and Columns
        for (int i = 0; i < 3; i++) {  //todo prepopulate these with cell objects
            Cell[] rowCells = new Cell[]{boardMatrix[i][0], boardMatrix[i][1], boardMatrix[i][2]};
            cell = findTwoInARow(token, rowCells);
            if (cell != null){
                cellSet.add(cell);
            }

            Cell[] columnCells = new Cell[]{boardMatrix[0][i], boardMatrix[1][i], boardMatrix[2][i]};
            cell = findTwoInARow(token, columnCells);
            if (cell != null){
                cellSet.add(cell);
            }
        }

        // check Diagonals
        Cell[] forwardDiagonalCells = new Cell[]{boardMatrix[0][0], boardMatrix[1][1], boardMatrix[2][2]};
        cell = findTwoInARow(token, forwardDiagonalCells);
        if(cell != null){
            cellSet.add(cell);
        }

        Cell[] backwardDiagonalCells = new Cell[]{boardMatrix[0][2], boardMatrix[1][1], boardMatrix[2][0]};
        cell = findTwoInARow(token, backwardDiagonalCells);
        if(cell != null){
            cellSet.add(cell);
        }

        return cellSet;
    }

    boolean makeWinningMove(String token){
        HashSet<Cell> cellSet = getWinningMovesSet(token);
        if (cellSet.size() > 0){
            Cell winningCell = cellSet.iterator().next();
            makeMove(winningCell, token);
            return true;
        }
        return false;
    }

    void makeRandomMove(String token) {
        ArrayList<Cell> availableCells = getAvailableMovesList();
        int availableMoveCount = availableCells.size();
        int randomMoveId = ThreadLocalRandom.current().nextInt(0, availableMoveCount);
        Cell randomCell = availableCells.get(randomMoveId);
        makeMove(randomCell, token);
    }

    boolean blockWinningMove(String myToken, String opponentToken){  //todo unitTest
        HashSet<Cell> cellSet = getWinningMovesSet(opponentToken);
        if (cellSet.size() > 0){
            Cell winningCell = cellSet.iterator().next();
            makeMove(winningCell, myToken);
            return true;
        }
        return false;
    }

    Cell findTwoInARow(String token, Cell[] rowColOrDiagonal){
        int tokenCounter = 0;
        int emptyCounter = 0;
        int emptyIndex = 0;

        for (int i = 0; i < 3; i++){
            Cell cellToCheck = rowColOrDiagonal[i];
            if (cellToCheck.getToken().equals(token)){
                tokenCounter++;
            } else if (cellToCheck.getToken().equals(noToken)){
                emptyCounter++;
                emptyIndex = i;
            }
        }
        if (tokenCounter == 2 && emptyCounter == 1){
            return rowColOrDiagonal[emptyIndex];
        }
        return null;
    }

    boolean findForkOpportunity(String token){
        for (Cell cellToCheck : availableMovesList){
            cellToCheck.setToken(token);
            HashSet<Cell> winningMovesSet = getWinningMovesSet(token);
            cellToCheck.setToken(noToken);

            if (winningMovesSet.size() > 1) {
                makeMove(cellToCheck, token);
                return true;
            }
        }
        return false;
    }

    boolean blockOpponentFork(String myToken, String opponentToken){
        Cell bestBlock = null;
        int currentWinOpportunities = 0;

        for (Cell cellToCheck : availableMovesList){
            cellToCheck.setToken(opponentToken);
            HashSet<Cell> opponentWinningMovesSet = getWinningMovesSet(opponentToken);
            cellToCheck.setToken(noToken);

            if (opponentWinningMovesSet.size() > 1) {

                // departure from the "find my own fork" method - look for my own winnings when blocking.
                cellToCheck.setToken(myToken);
                HashSet<Cell> myWinningMovesSet = getWinningMovesSet(myToken);
                cellToCheck.setToken(noToken);

                if (myWinningMovesSet.size() >= currentWinOpportunities) {
                    bestBlock = cellToCheck;
                    currentWinOpportunities = myWinningMovesSet.size();
                }
            }
        }
        if (bestBlock != null){
            makeMove(bestBlock, myToken);
            return true;
        }
        return false;
    }

    boolean findCenterOpportunity(String token) {
        Cell centerCell = boardMatrix[1][1];
        if (centerCell.getToken().equals(noToken)) {
            makeMove(centerCell, token);
            return true;
        }
        return false;
    }

    boolean findCornerOpportunity(String myToken, String otherToken) {
        Cell validCorner = null;

        for (Cell cellToCheck : availableMovesList){
            int rowToCheck = cellToCheck.getRow();
            int colToCheck = cellToCheck.getCol();

            // check if move is a corner move  //todo break out into method?
            if ((rowToCheck == 0 || rowToCheck == 2) && (colToCheck == 0 || colToCheck == 2)) {

                validCorner = cellToCheck;

                // get opposite corner
                int oppositeCornerRow = rowToCheck ^ 2;
                int oppositeCornerColumn = colToCheck ^ 2;

                Cell oppositeCornerCell = boardMatrix[oppositeCornerRow][oppositeCornerColumn];
                // if opposite corner is enemy-controlled, return move.
                if (oppositeCornerCell.getToken().equals(otherToken)) {
                    makeMove(cellToCheck, myToken);
                    return true;
                }
            }
        }
        if (validCorner != null) {
            makeMove(validCorner, myToken);
            return true;
        }
        return false;
    }

    boolean findEdgeOpportunity(String token) {

        for (Cell cellToCheck : availableMovesList){
            int row = cellToCheck.getRow();
            int col = cellToCheck.getCol();

            // check if edge cell. //todo break out into method?
            if((row == 0 && col == 1) || (row == 1 && col == 0) || (row == 1 && col == 2) || (row == 2 && col == 1)){
                makeMove(cellToCheck, token);
                return true;
            }
        }
        return false;
    }
}
