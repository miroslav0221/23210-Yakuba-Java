package org.example.Model;

import java.util.Arrays;
import org.example.Model.Cell;
import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class Figure {
    private Cell[] matrix;
    private String colour;
    private int x;
    private int y;

    public Figure() {
        matrix = new Cell[4*4];
        x = 4;
        y = 0;
    }

    public Cell[] getMatrix() {
        return matrix;
    }

    public void copy(Figure figureCopy) {
        Cell[] matrixCopy = figureCopy.getMatrix();
        for (int i = 0; i < 16; i++) {
            Cell cell = new Cell();
            cell.setStatus(matrixCopy[i].getStatus());
            cell.setColour(matrixCopy[i].getColour());
            matrix[i] = cell;
        }
        this.colour = figureCopy.getColour();
        this.x = figureCopy.getX();
        this.y = figureCopy.getY();
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public Cell getCell(int index) {
        return matrix[index];
    }

    void setMatrix(Cell[] matrixNew) {
        matrix = matrixNew;
    }

    public void dicrX() {
        x--;
    }
    public void incrX() {
        x++;
    }

    public void changeColour(String colourNew) {
        colour = colourNew;
        for (int i = 0; i < 16; i++) {
            if (matrix[i].getStatus() == Cell.cells.filled) {
                matrix[i].setColour(colour);
            }
        }
    }

    public void setColour(String colourNew) {
        colour = colourNew;

    }

    public String getColour() {
        return colour;
    }

    void rotate() {
        Cell[] matrixNew = new Cell[16];
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                matrixNew[j*4 + 3 - i] = matrix[i*4 + j];

            }
        }
        matrix = matrixNew;
    }

    void incrY() {
        y++;
    }


}
