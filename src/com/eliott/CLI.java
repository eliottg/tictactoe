package com.eliott;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Created by egray on 3/20/2018.
 */
class CLI {

    private Game game;

    void startInterface(){

        while (true) {
            int selection = getUserStartingChoice();
            if (selection == 4) {
                System.out.println("Bye!");
                System.exit(0);
            } else {
                playGame(selection);
            }
        }
    }

    private int getUserStartingChoice(){
        int selection = -1;
        while(selection < 1 || selection > 4) {
            try{
                Scanner sc = new Scanner(System.in);
                printWelcome();
                selection = sc.nextInt();
            } catch (InputMismatchException e){
                System.out.println("Error - enter a listed number.");
            }
        }
        return selection;
    }

    private void printWelcome(){
        String welcomeString = "Would you like to play a game?\r\n"
                + "1)  Easy\r\n"
                + "2)  Hard\r\n"
                + "3)  Impossible\r\n"
                + "4)  Quit\r\n"
                + ">";
        System.out.print(welcomeString);
    }

    private void printBoard(){
        Move[][] boardMatrix = game.board.getBoardMatrix();
        String header = "    1   2   3";
        String highLowSep = "   -----------";
        String row = "%d | %s | %s | %s |";
        String rowSep = "  |---+---+---|";
        System.out.println(header);
        System.out.println(highLowSep);
        System.out.println(String.format(row, 1, boardMatrix[0][0].getToken(), boardMatrix[0][1].getToken(), boardMatrix[0][2].getToken()));
        System.out.println(rowSep);
        System.out.println(String.format(row, 2, boardMatrix[1][0].getToken(), boardMatrix[1][1].getToken(), boardMatrix[1][2].getToken()));
        System.out.println(rowSep);
        System.out.println(String.format(row, 3, boardMatrix[2][0].getToken(), boardMatrix[2][1].getToken(), boardMatrix[2][2].getToken()));
        System.out.println(highLowSep);
        System.out.println();
    }

    private void getMoveFromPlayer(){

        boolean moveSuccess = false;
        while(!moveSuccess){
            try{
                Scanner sc = new Scanner(System.in);
                System.out.print("Enter a valid move: row then column. e.g. 2 2\r\n>");
                int row = sc.nextInt()-1;
                int col = sc.nextInt()-1;
                moveSuccess = game.makeManualMove(row, col);
            } catch (InputMismatchException e){
                System.out.println("Error - invalid entry.");
            }
        }
    }

    private void playGame(int difficulty){
        game = new Game(difficulty, "X", "O");
        boolean playerTurn = true;
        if(Math.random() < 0.5){
            System.out.println("Coin toss... Tails! Computer goes first!");
            game.makeComputerMove();
        } else{
            System.out.println("Coin toss... Heads! Player goes first!");

        }
        while(game.board.getGameState().equals("Ongoing")){
            if(playerTurn){
                printBoard();
                getMoveFromPlayer();
            } else {
                printBoard();
                System.out.println("Computer turn...\n");
                game.makeComputerMove();
            }
            if (game.board.getGameState().equals("Win")){
                if (playerTurn) {
                    printBoard();
                    System.out.println("You win!! :-D\r\n");
                }
                else {
                    printBoard();
                    System.out.println("You lose!! :-(\r\n");
                }
                break;
            } else if (game.board.getGameState().equals("Tie")){
                printBoard();
                System.out.println("It's a tie. :-/\r\n");
            }
            playerTurn = !playerTurn;
        }
    }
}
