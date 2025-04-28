package org.example.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class FigureTest {
    private Figure figure;

    @BeforeEach
    void setObject() {
        figure = new Figure();
    }

    @Test
    public void incrYTest() {
        figure.incrY();
        Assertions.assertEquals(1, figure.getY());
    }

    @Test
    public void incrXTest() {
        figure.incrX();
        Assertions.assertEquals(5, figure.getX());
    }

    @Test
    public void setGetMatrixTest() {
        Cell[] matrix = new Cell[4*4];
        matrix[0] = new Cell();
        matrix[0].setStatus(Cell.cells.filled);
        matrix[0].setColour("black");
        figure.setMatrix(matrix);
        Cell[] matrixNew = figure.getMatrix();
        Assertions.assertEquals(matrix, matrixNew);
    }

    @Test
    public void getCellTest() {
        Cell[] matrix = new Cell[4*4];
        Cell cell = new Cell();
        matrix[0] = cell;
        matrix[0].setStatus(Cell.cells.filled);
        matrix[0].setColour("black");
        figure.setMatrix(matrix);
        Cell cellTest = figure.getCell(0);
        Assertions.assertEquals(cell, cellTest);
    }

    @Test
    public void changeColourTest() {
        Cell[] matrix = new Cell[4*4];
        for (int i = 0; i < 16; i++) {
            Cell cell = new Cell();
            cell.setColour("black");
            cell.setStatus(Cell.cells.filled);
            matrix[i] = cell;
        }
        figure.setMatrix(matrix);

        figure.changeColour("blue");
        for (int i = 0; i < 16; i++) {
            Assertions.assertEquals("blue", figure.getCell(i).getColour());
        }
    }

    @Test
    public void rotateTest() {
        Cell[] matrix = new Cell[4*4];
        for (int i = 0; i < 16; i++) {
            if (i % 4 == 1) {
                matrix[i] = new Cell();
                matrix[i].setStatus(Cell.cells.filled);
            }
            else {
                matrix[i] = new Cell();
                matrix[i].setStatus(Cell.cells.empty);
            }
        }
        figure.setMatrix(matrix);
        figure.rotate();
        Cell[] matrixTest = new Cell[4*4];
        for (int i = 0; i < 16; i++) {
            if (i == 4 || i == 5 || i == 6 || i == 7) {
                matrixTest[i] = new Cell();
                matrixTest[i].setStatus(Cell.cells.filled);
            }
            else {
                matrixTest[i] = new Cell();
                matrixTest[i].setStatus(Cell.cells.empty);
            }
        }

        for (int i = 0; i < 16; i++) {
            Assertions.assertEquals(matrixTest[i].getStatus(), figure.getCell(i).getStatus());
        }
    }

    @Test
    public void copyTest() {
        Cell[] matrix = new Cell[4*4];
        for (int i = 0; i < 16; i++) {
            Cell cell = new Cell();
            cell.setColour("black");
            cell.setStatus(Cell.cells.filled);
            matrix[i] = cell;
        }
        figure.setMatrix(matrix);
        figure.setColour("red");

        Figure figureTest = new Figure();
        figureTest.copy(figure);


        for (int i = 0; i < 16; i++) {
            Assertions.assertEquals(figureTest.getCell(i).getStatus(),
                    figure.getCell(i).getStatus());
        }
        Assertions.assertEquals(figureTest.getColour(),
                figure.getColour());
    }
}
