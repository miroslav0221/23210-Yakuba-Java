package org.example.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ModelTest {
    private Model model;

    @BeforeEach
    void setObject() {
        model = new Model();
    }

    @Test
    void downTest() {
        Figure figure = new Figure();
        Cell[] matrix = new  Cell[4*4];
        for(int i = 0; i < 16; i++) {
            Cell cell = new Cell();
            cell.setStatus(Cell.cells.filled);
            matrix[i] = cell;
        }
        figure.setMatrix(matrix);
        Model.down(figure, model.getField());
        Assertions.assertEquals(1, figure.getY());
        while(Model.down(figure, model.getField())) {

        }
        Assertions.assertFalse(Model.down(figure, model.getField()));
    }

    @Test
    void rightTest() {
        Figure figure = new Figure();
        Cell[] matrix = new  Cell[4*4];
        for(int i = 0; i < 16; i++) {
            Cell cell = new Cell();
            cell.setStatus(Cell.cells.filled);
            matrix[i] = cell;
        }
        figure.setMatrix(matrix);
        Model.right(figure, model.getField());
        Assertions.assertEquals(5, figure.getX());
    }

    @Test
    void leftTest() {
        Figure figure = new Figure();
        Cell[] matrix = new  Cell[4*4];
        for(int i = 0; i < 16; i++) {
            Cell cell = new Cell();
            cell.setStatus(Cell.cells.filled);
            matrix[i] = cell;
        }
        figure.setMatrix(matrix);
        Model.left(figure, model.getField());
        Assertions.assertEquals(3, figure.getX());
    }

    @Test
    void rotateTest() {
        Figure figure = new Figure();
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
        Model.rotate(figure, model.getField());
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
    void removeLineTest() {
        Cell[] matrixTest = model.getField().getMatrix();
        for (int i = 230; i < 240; i++) {
            Cell cell = new Cell();
            cell.setStatus(Cell.cells.filled);
            matrixTest[i] = cell;
        }
        model.removeLine(23);
        for(Cell cell : model.getField().getMatrix()) {
            Assertions.assertEquals(Cell.cells.empty, cell.getStatus());
        }
    }

    @Test
    void checkFillRowTest() {

        int result = model.checkFillRow();
        Assertions.assertEquals(-1, result);

        Cell[] matrixTest = model.getField().getMatrix();
        for (int i = 230; i < 240; i++) {
            Cell cell = new Cell();
            cell.setStatus(Cell.cells.filled);
            matrixTest[i] = cell;
        }
        result = model.checkFillRow();
        Assertions.assertEquals(23, result);
    }

    @Test
    void checkPositionFigureTest() {
        Cell[] matrixTest = model.getField().getMatrix();
        for (int i = 230; i < 240; i++) {
            Cell cell = new Cell();
            cell.setStatus(Cell.cells.filled);
            matrixTest[i] = cell;
        }
        Figure figure = new Figure();
        Cell[] matrixFigure = new Cell[16];
        for (int i = 0; i < 16; i++) {
            Cell cell = new Cell();
            if (i == 1 || i == 5 || i == 9 || i == 13) {
                cell.setStatus(Cell.cells.filled);
            }
            else {
                cell.setStatus(Cell.cells.empty);
            }
            matrixFigure[i] = cell;
        }
        figure.setMatrix(matrixFigure);

        Assertions.assertTrue(Model.checkPositionFigure(figure, model.getField(), figure.getX(),
                figure.getY()));
        Assertions.assertFalse(Model.checkPositionFigure(figure, model.getField(), -2,
                figure.getY()));
        while(Model.down(figure, model.getField())) {

        }
        Assertions.assertFalse(Model.checkPositionFigure(figure, model.getField(), -2,
                figure.getY()+1));
    }

    @Test
    void mergeFigureToFieldTest() {
        MyField fieldTest = new MyField();
        Cell[] matrixTest = fieldTest.getMatrix();
        for (int i = 0; i < 4; i++) {
            Cell cell = new Cell();
            cell.setStatus(Cell.cells.filled);
            matrixTest[i*10 + 5] = cell;
        }


        Figure figure = new Figure();
        Cell[] matrixFigure = new Cell[16];
        for (int i = 0; i < 16; i++) {
            Cell cell = new Cell();
            if (i == 1 || i == 5 || i == 9 || i == 13) {
                cell.setStatus(Cell.cells.filled);
            }
            else {
                cell.setStatus(Cell.cells.empty);
            }
            matrixFigure[i] = cell;
        }
        figure.setMatrix(matrixFigure);
        model.mergeFigureToField(figure, model.getField());
        for(int i = 0; i < 240; i++) {
            System.out.println(i);
            Assertions.assertEquals(fieldTest.getCell(i).getStatus(),
                    model.getField().getCell(i).getStatus());
        }
    }

    @Test
    void offsetLimitPositionTest() {
        Figure figure = new Figure();
        Cell[] matrix = new  Cell[4*4];
        for(int i = 0; i < 16; i++) {
            Cell cell = new Cell();
            cell.setStatus(Cell.cells.filled);
            matrix[i] = cell;
        }
        figure.setMatrix(matrix);
        model.offsetLimitPosition(figure, model.getField());
        Assertions.assertEquals(20, figure.getY());
    }
}
