package com.eliott;

import static org.junit.Assert.*;
import org.junit.*;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.InputMismatchException;
import java.util.NoSuchElementException;


/**
 * Created by egray on 3/20/2018.
 */
//public class CLITest {
//
//    private CLI CLI;
//    private Board board;
//
//    @Before
//    public void setUp() throws Exception {
//        CLI = new CLI();
//        board = new Board();
//    }
//
//    @Test
//    public void testGetUserStartingChoice_easy(){
//        String testInputString = "0";
//        System.setIn(new ByteArrayInputStream(testInputString.getBytes()));
//        int difficulty = CLI.getUserStartingChoice();
//        assertEquals(0, difficulty);
//    }
//
//    @Test
//    public void testGetUserStartingChoice_hard(){
//        String testInputString = "1";
//        System.setIn(new ByteArrayInputStream(testInputString.getBytes()));
//        int difficulty = CLI.getUserStartingChoice();
//        assertEquals(1, difficulty);
//    }
//
//    @Test
//    public void testGetUserStartingChoice_quit(){
//        String testInputString = "2";
//        System.setIn(new ByteArrayInputStream(testInputString.getBytes()));
//        int difficulty = CLI.getUserStartingChoice();
//        assertEquals(2, difficulty);
//    }
//
//    @Test
//    public void testPrintWelcome(){
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outStream));
//        CLI.printWelcome();
//        String outString = outStream.toString();
//        String expectedString = "Would you like to play a game?\r\n"
//                + "0)  Easy\r\n"
//                + "1) 'Hard' (Impossible)\r\n"
//                + "2)  Quit CLI\r\n"
//                + ">";
//        assertEquals(expectedString, outString);
//    }
//
//    @Test
//    public void testPrintBoard(){
//        String[][] boardMatrix = {{"O", "X", "O"}, {"O", " ", "X"}, {" ", " ", " "}};
//        Board board = new Board();
//        board.setBoardMatrix(boardMatrix);
//        ByteArrayOutputStream outStream = new ByteArrayOutputStream();
//        System.setOut(new PrintStream(outStream));
//        CLI.printBoard();
//        String outString = outStream.toString();
//        String expectedString =
//                  "    1   2   3\r\n"
//                + "   -----------\r\n"
//                + "1 | O | X | O |\r\n"
//                + "  |---+---+---|\r\n"
//                + "2 | O |   | X |\r\n"
//                + "  |---+---+---|\r\n"
//                + "3 |   |   |   |\r\n"
//                + "   -----------\r\n";
//        assertEquals(expectedString, outString);
//    }
//
//    @Test
//    public void testGetMoveFromPlayer_valid(){
//        String testInputString = "2 2";
//        System.setIn(new ByteArrayInputStream(testInputString.getBytes()));
//        Cell move = CLI.getMoveFromPlayer();
//        assertNotNull(move);
//    }
//
//    @Test(expected = NoSuchElementException.class)
//    public void testGetMoveFromPlayer_invalidNumbers(){
//        String testInputString = "0 0"; // column or row should be 1, 2, or 3
//        System.setIn(new ByteArrayInputStream(testInputString.getBytes()));
//        CLI.getMoveFromPlayer();
//    }
//
//    @Test(expected = InputMismatchException.class)
//    public void testGetMoveFromPlayer_notNumbers(){
//        String testInputString = "foo bar"; // column or row should be 1, 2, or 3
//        System.setIn(new ByteArrayInputStream(testInputString.getBytes()));
//        CLI.getMoveFromPlayer();
//    }
//
//    @After
//    public void tearDown() throws Exception {
//        System.setIn(System.in);
//    }
//
//}
