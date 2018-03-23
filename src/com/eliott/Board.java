package com.eliott;

import java.util.ArrayList;
import java.util.HashSet;

/**
 * Created by egray on 2/20/2018.
 */
class Board {

    private Move[][] boardMatrix;
    private ArrayList<Move> availableMovesList;

    Board() {
        boardMatrix = new Move[3][3];
        availableMovesList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                Move newMove = new Move(i, j, " ");
                boardMatrix[i][j] = newMove;
                availableMovesList.add(newMove);

            }
        }
    }


    Move[][] getBoardMatrix() { return boardMatrix; }

    ArrayList<Move> getAvailableMovesList() { return availableMovesList; }

    void setBoardMatrix(String[][] newBoardMatrix) {
        availableMovesList = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                String newToken = newBoardMatrix[i][j];
                Move newMove = new Move(i, j, newToken);
                boardMatrix[i][j] = newMove;
                if(newToken.equals(" ")){
                    availableMovesList.add(newMove);
                }
            }
        }
    }

    void addMove(Move move) {
        boardMatrix[move.getRow()][move.getCol()] = move;
        removeMoveFromAvailableMoveList(move);
    }

    void removeMoveFromAvailableMoveList(Move moveToRemove) {
        Move move = null;   //todo Ensure that incoming moves is the same reference, so this is unnecessary?
        for ( Move availableMove : availableMovesList) {
            if (availableMove.getRow() == moveToRemove.getRow() && availableMove.getCol() == moveToRemove.getCol()){
                move = availableMove;
                break;
            }
        }
        availableMovesList.remove(move);
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
        for (int i = 0; i < 3; i++) {
            Move[] row = new Move[]{boardMatrix[i][0], boardMatrix[i][1], boardMatrix[i][2]};
            if (checkRowForVictory(row)){
                return true;
            }

            Move[] column = new Move[]{boardMatrix[0][i], boardMatrix[1][i], boardMatrix[2][i]};
            if (checkRowForVictory(column)){
                return true;
            }
        }

        Move[] frontDiagonal = new Move[]{boardMatrix[0][0], boardMatrix[1][1], boardMatrix[2][2]};
        if (checkRowForVictory(frontDiagonal)){
            return true;
        }

        Move[] backDiagonal = new Move[]{boardMatrix[0][2], boardMatrix[1][1], boardMatrix[2][0]};
        if (checkRowForVictory(backDiagonal)){
            return true;
        }

        return false;
    }

    boolean checkRowForVictory(Move[] row) {
        String cellOneToken = row[0].getToken();
        String cellTwoToken = row[1].getToken();
        String cellThreeToken = row[2].getToken();
        boolean rowIsIdentical = cellOneToken.equals(cellTwoToken) && cellTwoToken.equals(cellThreeToken);
        boolean rowIsNotEmpty = !cellOneToken.equals(" ");
        return rowIsIdentical && rowIsNotEmpty;
    }

    boolean boardIsFull() {
        return availableMovesList.size() == 0;
    }

    HashSet<Move> getWinningMovesSet(String token) {
        HashSet<Move> moveSet = new HashSet<>();
        Move move;
        // check Rows and Columns
        for (int i = 0; i < 3; i++) {
            int[][] rowCoordinates = new int[][]{{i, 0}, {i, 1}, {i, 2}};
            move = findTwoInARow(token, rowCoordinates);
            if (move != null){
                moveSet.add(move);
            }

            int[][] columnCoordinates = new int[][]{{0, i},{1, i},{2, i}};
            move = findTwoInARow(token, columnCoordinates);
            if (move != null){
                moveSet.add(move);
            }
        }

        // check Diagonals
        int[][] forwardDiagonalCoords = new int[][]{{0, 0},{1, 1},{2, 2}};
        move = findTwoInARow(token, forwardDiagonalCoords);
        if(move != null){
            moveSet.add(move);
        }

        int[][] backwardDiagonalCoords = new int[][]{{0, 2},{1, 1},{2, 0}};
        move = findTwoInARow(token, backwardDiagonalCoords);
        if(move != null){
            moveSet.add(move);
        }

        return moveSet;
    }

    Move getWinningMove(String token){
        Move winningMove = null;
        HashSet<Move> moveSet = getWinningMovesSet(token);
        if (moveSet.size() > 0){
            winningMove = moveSet.iterator().next();
        }

        return winningMove;
    }

    Move findTwoInARow(String token, int[][] rowColOrDiagonal){
        Move boardCell;
        int boardRowCoordinate;
        int boardColumnCoordinate;
        int tokenCounter = 0;
        int emptyCounter = 0;
        int emptyIndex = 0;
        int[] winningCoordinates;

        for (int i = 0; i < 3; i++){
            boardRowCoordinate = rowColOrDiagonal[i][0];
            boardColumnCoordinate = rowColOrDiagonal[i][1];
            boardCell = boardMatrix[boardRowCoordinate][boardColumnCoordinate];
            if (boardCell.getToken().equals(token)){
                tokenCounter++;
            } else if (boardCell.getToken().equals(" ")){
                emptyCounter++;
                emptyIndex = i;
            }
        }
        if (tokenCounter == 2 && emptyCounter == 1){
            winningCoordinates = rowColOrDiagonal[emptyIndex];
            return new Move(winningCoordinates[0], winningCoordinates[1], token);  //todo reuse existing move
        }
        return null;
    }

    Move findForkOpportunity(String token){
        Move move = null;

        for (Move moveToCheck : availableMovesList){
            int row = moveToCheck.getRow();
            int col = moveToCheck.getCol();
            boardMatrix[row][col].setToken(token);
            HashSet<Move> winningMovesSet = getWinningMovesSet(token);
            boardMatrix[row][col].setToken(" ");

            if (winningMovesSet.size() > 1) {
                move = new Move(row, col, token);  //todo reuse existing move
                break;
            }
        }
        return move;
    }

    Move blockOpponentFork(String myToken, String opponentToken){   //todo unit test.
        Move move = null;

        for (Move moveToCheck : availableMovesList){
            int row = moveToCheck.getRow();
            int col = moveToCheck.getCol();

            boardMatrix[row][col].setToken(opponentToken);
            HashSet<Move> opponentWinningMovesSet = getWinningMovesSet(opponentToken);
            boardMatrix[row][col].setToken(" ");

            if (opponentWinningMovesSet.size() > 1) {

                // departure from the "find my own fork" method - look for my own winnings when blocking.
                boardMatrix[row][col].setToken(myToken);
                HashSet<Move> myWinningMovesSet = getWinningMovesSet(myToken);
                boardMatrix[row][col].setToken(" ");

                if (myWinningMovesSet.size() > 0) {
                    move = new Move(row, col, myToken); //todo reuse existing move
                    break;
                }
            }
        }
        return move;
    }

    Move findCenterOpportunity(String token) {
        Move move = null;

        if (boardMatrix[1][1].getToken().equals(" ")) {
            move = new Move(1, 1, token);  //todo reuse existing move
        }

        return move;
    }

    Move findCornerOpportunity(String myToken, String otherToken) {
        Move cornerMove = null;

        for (Move moveToCheck : availableMovesList){
            int row = moveToCheck.getRow();
            int col = moveToCheck.getCol();

            // check if move is a corner move  //todo break out into method?
            if ((row == 0 || row == 2) && (col == 0 || col == 2)) {

                cornerMove = new Move(row, col, myToken);   //todo reuse existing move...

                // get opposite corner
                int oppositeCornerRow = row ^ 2;
                int oppositeCornerColumn = col ^ 2;

                String oppositeCornerToken = boardMatrix[oppositeCornerRow][oppositeCornerColumn].getToken();
                // if opposite corner is enemy-controlled, return move.
                if (oppositeCornerToken.equals(otherToken)) {
                    return cornerMove;
                }
            }
        }
        return cornerMove;
    }

    Move findEdgeOpportunity(String token) {
        Move edgeMove = null;

        for (Move moveToCheck : availableMovesList){
            int row = moveToCheck.getRow();
            int col = moveToCheck.getCol();

            // check if edge cell.
            if((row == 0 && col == 1) || (row == 1 && col == 0) || (row == 1 && col == 2) || (row == 2 && col == 1)){
                edgeMove = new Move(row, col, token);  //todo reuse existing move.
                break;
            }
        }

        return edgeMove;
    }

}
