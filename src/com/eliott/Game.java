package com.eliott;

import java.util.ArrayList;
import java.util.HashSet;
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
        return row < 3 && row >= 0 && col < 3 && col >= 0 && board.getBoardMatrix()[row][col].getToken().equals(" ");
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
        HashSet<Move> moveSet = board.getWinningMovesSet(computerToken);
        if (moveSet.size() > 0){
            return moveSet.iterator().next();
        }

        return getRandomMove();
    }

    Move getRandomMove() {
        ArrayList<Move> availableMoves = board.getAvailableMovesList();

        int availableMoveCount = availableMoves.size();
        int randomMoveId = ThreadLocalRandom.current().nextInt(0, availableMoveCount);
        Move randomMove = availableMoves.get(randomMoveId);
        randomMove.setToken(computerToken);
        return randomMove;
    }

    Move getHardMove() {
        Move move;

        move = board.getWinningMove(computerToken);
        if (move != null) {
            return move;
        }

        move = board.getWinningMove(playerToken);
        if (move != null) {
            move.setToken(computerToken);  //todo Avoid having to override auto-built move with new token OR make into method.
            return move;
        }

        return getRandomMove();
    }

    Move getImpossibleMove() {
        Move move;

        move = board.getWinningMove(computerToken);
        if (move != null) {
            return move;
        }

        move = board.getWinningMove(playerToken);
        if (move != null) {
            move.setToken(computerToken);  //todo Avoid having to override auto-built move with new token OR make into method.
            return move;
        }

        move = board.findForkOpportunity(computerToken);
        if (move != null) {
            return move;
        }

        move = board.blockOpponentFork(computerToken, playerToken);
        if (move != null) {
            return move;
        }

        move = board.findCenterOpportunity(computerToken);
        if (move != null) {
            return move;
        }

        move = board.findCornerOpportunity(computerToken, playerToken);
        if (move != null){
            return move;
        }

        return board.findEdgeOpportunity(computerToken);

    }


}
