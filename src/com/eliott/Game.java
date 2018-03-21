package com.eliott;

import java.util.Scanner;

/**
 * Created by egray on 3/20/2018.
 */
class Game {

    void startGame(){

        while (true){
            printWelcome();
            int selection = -1;
            while(selection < 0 || selection > 2) {
                selection = getUserStartingChoice();
            }
            if (selection == 0){
                easyGame();
            } else if(selection == 1){
                System.out.println("---ERROR: not implemented yet.");
            } else {
                System.out.println("Bye!");
                System.exit(0);
            }
        }

    }

    int getUserStartingChoice(){
        Scanner sc = new Scanner(System.in);
        int value = sc.nextInt();
        return value;
    }

    void printWelcome(){
        String welcomeString = "Would you like to play a game?\r\n"
                + "0)  Easy\r\n"
                + "1) 'Hard' (Impossible)\r\n"
                + "2)  Quit Game\r\n"
                + ">";
        System.out.print(welcomeString);
    }

    void printBoard(Board board){
        String[][] boardMatrix = board.getBoardMatrix();
        String header = "    1   2   3";
        String highLowSep = "   -----------";
        String row = "%d | %s | %s | %s |";
        String rowSep = "  |---+---+---|";
        System.out.println(header);
        System.out.println(highLowSep);
        System.out.println(String.format(row, 1, boardMatrix[0][0], boardMatrix[0][1], boardMatrix[0][2]));
        System.out.println(rowSep);
        System.out.println(String.format(row, 2, boardMatrix[1][0], boardMatrix[1][1], boardMatrix[1][2]));
        System.out.println(rowSep);
        System.out.println(String.format(row, 3, boardMatrix[2][0], boardMatrix[2][1], boardMatrix[2][2]));
        System.out.println(highLowSep);
    }

    Move getMoveFromPlayer(Board board){
        Move move = new Move(-1, -1, "X", false);
        while(!board.moveIsValid(move)){
            System.out.print("Enter a valid move: row then column. e.g. 2 2\r\n>");
            Scanner sc = new Scanner(System.in);
            int row = sc.nextInt()-1;
            int column = sc.nextInt()-1;
            move = new Move(row, column, "X", false);
        }
        return move;
    }

    void easyGame(){
        Board board = new Board();
        Opponent opponent = new Opponent("O");
        boolean playerTurn = true;
        if(Math.random() < 0.5){
            System.out.println("Coin toss... Tails! Computer goes first!");
            Move randomMove = opponent.getEasyMove(board, "O");
            board.addMove(randomMove);
        } else{
            System.out.println("Coin toss... Heads! Player goes first!");

        }
        while(board.getGameState().equals("Ongoing")){
            if(playerTurn){
                printBoard(board);
                Move move = getMoveFromPlayer(board);
                board.addMove(move);
                printBoard(board);
            } else {
                Move randomMove = opponent.getEasyMove(board, "O");
                System.out.println("Computer turn...\n");
                board.addMove(randomMove);
            }
            if (board.getGameState().equals("Win")){
                if (playerTurn) {
                    printBoard(board);
                    System.out.println("You win!! :-D\r\n");
                }
                else {
                    printBoard(board);
                    System.out.println("You lose!! :-(\r\n");
                }
                break;
            } else if (board.getGameState().equals("Tie")){
                printBoard(board);
                System.out.println("It's a tie. :-/\r\n");
            }

            playerTurn = !playerTurn;

        }
    }
}
