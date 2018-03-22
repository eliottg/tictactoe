package com.eliott;

import java.util.ArrayList;
import java.util.concurrent.ThreadLocalRandom;

/**
 * Created by egray on 2/20/2018.
 */
class Game {

    private int difficultyValue;
    private String computerToken;
    private String playerToken;
    Board board;

    Game(int difficultyValue, String playerToken, String computerToken){
        this.difficultyValue = difficultyValue;
        this.board = new Board();
        this.playerToken = playerToken;
        this.computerToken = computerToken;
    }

    boolean moveIsValid(Move move) {
        int row = move.getRow();
        int col = move.getCol();
        return row < 3 && row >= 0 && col < 3 && col >= 0 && board.getBoardMatrix()[row][col].equals(" ");
    }

    void makeComputerMove() {
        Move move = new Move(-1, -1, computerToken);  //todo Remove this invalid move creation hack.
        if (difficultyValue == 1) {
            move = getEasyMove();
        } else if (difficultyValue == 2) {
            move = getHardMove();
        } else if (difficultyValue == 3) {
            move = getImpossibleMove();
        }
        board.addMove(move);
    }

    void makeManualMove(Move move){
        if (moveIsValid(move)){
            board.addMove(move);
        } else {
            throw new IllegalStateException("Invalid move made. Validity should be checked earlier.");
        }
    }

    Move getEasyMove() {
        Move move = board.searchBoardForWinningMove(computerToken);
        if (move != null){
            return move;
        }

        return getRandomMove();
    }

    Move getRandomMove() {
        ArrayList<int[]> availableMoves = board.getListOfAvailableMoves();
        int availableMoveCount = availableMoves.size();
        int randomMoveId = ThreadLocalRandom.current().nextInt(0, availableMoveCount);
        int row = availableMoves.get(randomMoveId)[0];
        int col = availableMoves.get(randomMoveId)[1];

        return new Move(row, col, computerToken);
    }

    Move getHardMove() {
        Move move;

        move = board.searchBoardForWinningMove(computerToken); // Find computer win.
        if (move!= null) {
            return move;
        }

        move = board.searchBoardForWinningMove(playerToken); // Block player win.
        if (move != null) {
            return new Move(move.getRow(), move.getCol(), computerToken); //Todo have to hack this to reuse the winning move method, which auto-creates the same move as the input token.
        }

        return getRandomMove();
    }

    Move getImpossibleMove() {
        Move move;

        move = board.searchBoardForWinningMove(computerToken); // Find computer win.
        if (move!= null) {
            return move;
        }

        move = board.searchBoardForWinningMove(playerToken); // Block player win.
        if (move != null) {
            return new Move(move.getRow(), move.getCol(), computerToken); //Todo have to hack this to reuse the winning move method, which auto-creates the same move as the input token.
        }

        //todo: Implement check for possible forks
        //todo: Check for blocking forks
        //todo: Check for priority of remaining moves: Center, opposite corner, empty corner, empty side

        return getRandomMove(); //todo: Deprecate.
    }


}
