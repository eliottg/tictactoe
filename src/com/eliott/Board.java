package com.eliott;

import java.util.ArrayList;

/**
 * Created by egray on 2/20/2018.
 */
class Board {

    private String[][] boardMatrix;

    Board() {
        boardMatrix = new String[3][3];
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                boardMatrix[i][j] = " ";
            }
        }
    }


    String[][] getBoardMatrix() { return boardMatrix; }

    void setBoardMatrix(String[][] newBoardMatrix) {
        boardMatrix = newBoardMatrix;
    }

    void addMove(Move move) {
        int row = move.getRow();
        int col = move.getCol();
        String token = move.getToken();
        boardMatrix[row][col] = token;
    }

    boolean moveIsValid(Move move) {
        int row = move.getRow();
        int col = move.getCol();
        return row < 3 && row >= 0 && col < 3 && col >= 0 && boardMatrix[row][col].equals(" ");
    }

    ArrayList<int[]> getListOfAvailableMoves(){
        ArrayList<int[]> availableMoves = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if(boardMatrix[i][j].equals(" ")){
                    availableMoves.add(new int[]{i, j});
                }
            }
        }
        return availableMoves;
    }

    String getGameState() {
        if (isVictory()) {
            return "Win";
        } else if (isFull()) {
            return "Tie";
        } else {
            return "Ongoing";
        }
    }

    boolean isVictory() {
        for (int i = 0; i < 3; i++) {
            String[] row = new String[]{boardMatrix[i][0], boardMatrix[i][1], boardMatrix[i][2]};
            if (checkRowForVictory(row)){
                return true;
            }

            String[] column = new String[]{boardMatrix[0][i], boardMatrix[1][i], boardMatrix[2][i]};
            if (checkRowForVictory(column)){
                return true;
            }
        }

        String[] frontDiagonal = new String[]{boardMatrix[0][0], boardMatrix[1][1], boardMatrix[2][2]};
        if (checkRowForVictory(frontDiagonal)){
            return true;
        }

        String[] backDiagonal = new String[]{boardMatrix[0][2], boardMatrix[1][1], boardMatrix[2][0]};
        if (checkRowForVictory(backDiagonal)){
            return true;
        }

        return false;
    }

    boolean checkRowForVictory(String[] row) {
        boolean rowIsIdentical = row[0].equals(row[1]) && row[1].equals(row[2]);
        boolean rowIsNotEmpty = !row[0].equals(" ");
        return rowIsIdentical && rowIsNotEmpty;
    }

    boolean isFull() {
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                if (boardMatrix[i][j].equals(" ")){
                    return false;
                }
            }
        }
        return true;
    }

    Move searchBoardForWinningMove(String token) {
        Move move;
        // check Rows and Columns
        for (int i = 0; i < 3; i++) {
            int[][] rowCoordinates = new int[][]{{i, 0}, {i, 1}, {i, 2}};
            move = findTwoInARow(token, rowCoordinates);
            if (move.isWinningMove()){
                return move;
            }

            int[][] columnCoordinates = new int[][]{{0, i},{1, i},{2, i}};
            move = findTwoInARow(token, columnCoordinates);
            if (move.isWinningMove()){
                return move;
            }
        }

        // check Diagonals
        int[][] forwardDiagonalCoords = new int[][]{{0, 0},{1, 1},{2, 2}};
        move = findTwoInARow(token, forwardDiagonalCoords);
        if (move.isWinningMove()){
            return move;
        }

        int[][] backwardDiagonalCoords = new int[][]{{0, 2},{1, 1},{2, 0}};
        move = findTwoInARow(token, backwardDiagonalCoords);
        if (move.isWinningMove()){
            return move;
        }

        return new Move(-1, -1, token, false);
    }


    Move findTwoInARow(String token, int[][] rowColOrDiagonal){
        String boardCell;
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
            if (boardCell.equals(token)){
                tokenCounter++;
            } else if (boardCell.equals(" ")){
                emptyCounter++;
                emptyIndex = i;
            }
        }
        if (tokenCounter == 2 && emptyCounter == 1){
            winningCoordinates = rowColOrDiagonal[emptyIndex];
            return new Move(winningCoordinates[0], winningCoordinates[1], token, true);
        }
        return new Move(-1, -1, token, false);
    }
}
