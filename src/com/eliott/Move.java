package com.eliott;

/**
 * Created by egray on 2/21/2018.
 */

class Move {    //todo rename to Cell, instead of Move.

    private int row;
    private int col;
    private String token;

    Move(int row, int col, String token) {
        this.row = row;
        this.col = col;
        this.token = token;
    }

    void setToken(String token) { this.token = token; }
    int getRow() { return this.row; }
    int getCol() { return this.col; }
    String getToken() { return this.token; }
}
