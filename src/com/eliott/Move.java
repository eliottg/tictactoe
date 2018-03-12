package com.eliott;

/**
 * Created by egray on 2/21/2018.
 */
class Move {

    private int row;
    private int col;
    private String token;
    private boolean isWinningMove;

    Move(int row, int col, String token, boolean isWinningMove) {
        this.row = row;
        this.col = col;
        this.token = token;
        this.isWinningMove = isWinningMove;
    }

    int getRow() { return this.row; }
    int getCol() { return this.col; }
    String getToken() { return this.token; }
    boolean isWinningMove() { return this.isWinningMove; }
}
