package com.eliott;

import org.junit.*;
import static org.junit.Assert.*;

/**
 * Created by egray on 2/21/2018.
 */
public class CellTest {

    private Cell cell;

    @Before
    public void setUp() {
        cell = new Cell(0, 1, "X");
    }

    @Test
    public void testGetRow() {
        assertEquals(0, cell.getRow());
    }

    @Test
    public void testGetCol() {
        assertEquals(1, cell.getCol());
    }

    @Test
    public void testGetToken() {
        assertEquals("X", cell.getToken());
    }
}
