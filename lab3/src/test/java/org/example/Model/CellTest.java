package org.example.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CellTest {
    private Cell cell;


    @BeforeEach
    void setObject() {
        cell = new Cell();
    }

    @Test
    public void getStatusTest() {
        cell.setStatus(Cell.cells.filled);
        Assertions.assertEquals(Cell.cells.filled, cell.getStatus());
    }

    @Test
    public void getColourTest() {
        cell.setColour("black");
        Assertions.assertEquals("black", cell.getColour());
    }
}
