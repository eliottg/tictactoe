package com.eliott;


import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;

import static org.junit.Assert.*;

/**
 * Created by egray on 3/20/2018.
 */
public class GameTest{

    private Game game;
    private Board board;

    @Before
    public void setUp() throws Exception {
        game = new Game();
        board = new Board();
    }

//    public void testStartGame(){
//        ByteArrayOutputStream baos = new ByteArrayOutputStream();
//        String testInputString = "0";
//        System.setIn(new ByteArrayInputStream(testInputString.getBytes()));
//        System.setOut(new PrintStream(baos));
//        game.startGame();
//        String outString = baos.toString();
//        String expectedString = "Would you like to play a game?\r\n";
//        assertEquals(expectedString, outString);
//    }

    @Test
    public void testGetUserStartingChoice_easy(){
        String testInputString = "0";
        System.setIn(new ByteArrayInputStream(testInputString.getBytes()));
        int difficulty = game.getUserStartingChoice();
        assertEquals(0, difficulty);
    }

    @Test
    public void testGetUserStartingChoice_hard(){
        String testInputString = "1";
        System.setIn(new ByteArrayInputStream(testInputString.getBytes()));
        int difficulty = game.getUserStartingChoice();
        assertEquals(1, difficulty);
    }

    @Test
    public void testGetUserStartingChoice_quit(){
        String testInputString = "2";
        System.setIn(new ByteArrayInputStream(testInputString.getBytes()));
        int difficulty = game.getUserStartingChoice();
        assertEquals(2, difficulty);
    }

    @Test
    public void testPrintWelcome(){
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        game.printWelcome();
        String outString = outStream.toString();
        String expectedString = "Would you like to play a game?\r\n"
                + "0)  Easy\r\n"
                + "1) 'Hard' (Impossible)\r\n"
                + "2)  Quit Game\r\n"
                + ">";
        assertEquals(expectedString, outString);
    }

    @Test
    public void testPrintBoard(){
        String[][] boardMatrix = {{"O", "X", "O"}, {"O", " ", "X"}, {" ", " ", " "}};
        Board board = new Board();
        board.setBoardMatrix(boardMatrix);
        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outStream));
        game.printBoard(board);
        String outString = outStream.toString();
        String expectedString =
                  "    1   2   3\r\n"
                + "   -----------\r\n"
                + "1 | O | X | O |\r\n"
                + "  |---+---+---|\r\n"
                + "2 | O |   | X |\r\n"
                + "  |---+---+---|\r\n"
                + "3 |   |   |   |\r\n"
                + "   -----------\r\n";
        assertEquals(expectedString, outString);
    }

    @Test
    public void testGetMoveFromPlayer_valid(){
        String testInputString = "2 2";
        System.setIn(new ByteArrayInputStream(testInputString.getBytes()));
        Move move = game.getMoveFromPlayer(board);
        assertTrue(board.moveIsValid(move));
    }

    @Test(expected = NoSuchElementException.class)
    public void testGetMoveFromPlayer_invalidNumbers(){
        String testInputString = "0 0"; // column or row should be 1, 2, or 3
        System.setIn(new ByteArrayInputStream(testInputString.getBytes()));
        game.getMoveFromPlayer(board);
    }

    @Test(expected = InputMismatchException.class)
    public void testGetMoveFromPlayer_notNumbers(){
        String testInputString = "foo bar"; // column or row should be 1, 2, or 3
        System.setIn(new ByteArrayInputStream(testInputString.getBytes()));
        game.getMoveFromPlayer(board);
    }

    @After
    public void tearDown() throws Exception {
        System.setIn(System.in);
    }

}
