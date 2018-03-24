package com.eliott;

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

    boolean moveIsValid(int row, int col) {
        return row < 3 && row >= 0 && col < 3 && col >= 0 && board.getBoardMatrix()[row][col].getToken().equals(" ");
    }

    void makeComputerMove() {
        if (difficultyValue == 1) {
            makeEasyMove();
        } else if (difficultyValue == 2) {
            makeHardMove();
        } else if (difficultyValue == 3) {
            makeImpossibleMove();
        }
    }

    boolean makeManualMove(int row, int col){
        if (moveIsValid(row, col)){
            Move move = board.getBoardMatrix()[row][col];
            board.makeMove(move, playerToken);
            return true;
        } else {
            return false;
        }
    }

    void makeEasyMove() {
        if(board.makeWinningMove(computerToken)){ return; }
        board.makeRandomMove(computerToken);
    }

    void makeHardMove() {
        if(board.makeWinningMove(computerToken)){ return; }
        if(board.blockWinningMove(computerToken, playerToken)){ return; }
        board.makeRandomMove(computerToken);
    }

    void makeImpossibleMove() {
        if(board.makeWinningMove(computerToken)){ return; }
        if(board.blockWinningMove(computerToken, playerToken)){ return; }
        if(board.findForkOpportunity(computerToken)){ return; }
        if(board.blockOpponentFork(computerToken, playerToken)){ return; }
        if(board.findCenterOpportunity(computerToken)){ return; }
        if(board.findCornerOpportunity(computerToken, playerToken)){ return; }
        board.findEdgeOpportunity(computerToken);
    }

}
