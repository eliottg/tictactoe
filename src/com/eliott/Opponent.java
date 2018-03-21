package com.eliott;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by egray on 2/20/2018.
 */
class Opponent {
    private int difficultyValue = 0;
    private String token;

    Opponent(String token){
        this.token = token;
    }

    void setDifficulty(int difficulty){
        difficultyValue = difficulty;
    }

    Move getEasyMove(Board board, String token) {
        Move move = board.searchBoardForWinningMove(token);
        if (move != null){
            return move;
        } else {
            return getRandomMove(board, token);
        }
    }

    Move getRandomMove(Board board, String token) {
        ArrayList<int[]> availableMoves = board.getListOfAvailableMoves();
        int availableMoveCount = availableMoves.size();
        int randomMoveId = ThreadLocalRandom.current().nextInt(0, availableMoveCount);
        int row = availableMoves.get(randomMoveId)[0];
        int col = availableMoves.get(randomMoveId)[1];

        return new Move(row, col, token);
    }


}
