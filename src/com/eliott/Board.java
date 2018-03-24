package com.eliott;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.concurrent.ThreadLocalRandom;

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

    void makeMove(Move move, String token) {
        if(!move.getToken().equals(" ")){
            throw new IllegalStateException("Invalid move made. Validity should be checked earlier.");
        }
        move.setToken(token);
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
        for (int i = 0; i < 3; i++) { //todo prepopulate these with move objects
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
        for (int i = 0; i < 3; i++) {  //todo prepopulate these with move objects
            Move[] rowMoves = new Move[]{boardMatrix[i][0], boardMatrix[i][1], boardMatrix[i][2]};
            move = findTwoInARow(token, rowMoves);
            if (move != null){
                moveSet.add(move);
            }

            Move[] columnMoves = new Move[]{boardMatrix[0][i], boardMatrix[1][i], boardMatrix[2][i]};
            move = findTwoInARow(token, columnMoves);
            if (move != null){
                moveSet.add(move);
            }
        }

        // check Diagonals
        Move[] forwardDiagonalMoves = new Move[]{boardMatrix[0][0], boardMatrix[1][1], boardMatrix[2][2]};
        move = findTwoInARow(token, forwardDiagonalMoves);
        if(move != null){
            moveSet.add(move);
        }

        Move[] backwardDiagonalMoves = new Move[]{boardMatrix[0][2], boardMatrix[1][1], boardMatrix[2][0]};
        move = findTwoInARow(token, backwardDiagonalMoves);
        if(move != null){
            moveSet.add(move);
        }

        return moveSet;
    }

    boolean makeWinningMove(String token){
        HashSet<Move> moveSet = getWinningMovesSet(token);
        if (moveSet.size() > 0){
            Move winningMove = moveSet.iterator().next();
            makeMove(winningMove, token);
            return true;
        }
        return false;
    }

    void makeRandomMove(String token) {
        ArrayList<Move> availableMoves = getAvailableMovesList();
        int availableMoveCount = availableMoves.size();
        int randomMoveId = ThreadLocalRandom.current().nextInt(0, availableMoveCount);
        Move randomMove = availableMoves.get(randomMoveId);
        makeMove(randomMove, token);
    }

    boolean blockWinningMove(String myToken, String opponentToken){  //todo unitTest
        HashSet<Move> moveSet = getWinningMovesSet(opponentToken);
        if (moveSet.size() > 0){
            Move winningMove = moveSet.iterator().next();
            makeMove(winningMove, myToken);
            return true;
        }
        return false;
    }

    Move findTwoInARow(String token, Move[] rowColOrDiagonal){
        int tokenCounter = 0;
        int emptyCounter = 0;
        int emptyIndex = 0;

        for (int i = 0; i < 3; i++){
            Move moveToCheck = rowColOrDiagonal[i];
            if (moveToCheck.getToken().equals(token)){
                tokenCounter++;
            } else if (moveToCheck.getToken().equals(" ")){
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
        for (Move moveToCheck : availableMovesList){
            moveToCheck.setToken(token);
            HashSet<Move> winningMovesSet = getWinningMovesSet(token);
            moveToCheck.setToken(" ");

            if (winningMovesSet.size() > 1) {
                makeMove(moveToCheck, token);
                return true;
            }
        }
        return false;
    }

    boolean blockOpponentFork(String myToken, String opponentToken){
        Move bestBlock = null;
        int currentWinOpportunities = 0;

        for (Move moveToCheck : availableMovesList){
            moveToCheck.setToken(opponentToken);
            HashSet<Move> opponentWinningMovesSet = getWinningMovesSet(opponentToken);
            moveToCheck.setToken(" ");

            if (opponentWinningMovesSet.size() > 1) {

                // departure from the "find my own fork" method - look for my own winnings when blocking.
                moveToCheck.setToken(myToken);
                HashSet<Move> myWinningMovesSet = getWinningMovesSet(myToken);
                moveToCheck.setToken(" ");

                if (myWinningMovesSet.size() >= currentWinOpportunities) {
                    bestBlock = moveToCheck;
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
        Move centerMove = boardMatrix[1][1];
        if (centerMove.getToken().equals(" ")) {
            makeMove(centerMove, token);
            return true;
        }
        return false;
    }

    boolean findCornerOpportunity(String myToken, String otherToken) {
        Move validCorner = null;

        for (Move moveToCheck : availableMovesList){
            int rowToCheck = moveToCheck.getRow();
            int colToCheck = moveToCheck.getCol();

            // check if move is a corner move  //todo break out into method?
            if ((rowToCheck == 0 || rowToCheck == 2) && (colToCheck == 0 || colToCheck == 2)) {

                validCorner = moveToCheck;

                // get opposite corner
                int oppositeCornerRow = rowToCheck ^ 2;
                int oppositeCornerColumn = colToCheck ^ 2;

                Move oppositeCornerMove = boardMatrix[oppositeCornerRow][oppositeCornerColumn];
                // if opposite corner is enemy-controlled, return move.
                if (oppositeCornerMove.getToken().equals(otherToken)) {
                    makeMove(moveToCheck, myToken);
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

        for (Move moveToCheck : availableMovesList){
            int row = moveToCheck.getRow();
            int col = moveToCheck.getCol();

            // check if edge cell. //todo break out into method?
            if((row == 0 && col == 1) || (row == 1 && col == 0) || (row == 1 && col == 2) || (row == 2 && col == 1)){
                makeMove(moveToCheck, token);
                return true;
            }
        }
        return false;
    }
}
