package org.example.Model;

import org.example.Model.Cell;
import java.lang.reflect.Array;
import java.util.Arrays;

public class MyField {


    private Cell[] matrix;
    private int weight;
    private int height;

    public MyField() {
        matrix = new Cell[240];
        Cell cell = new Cell();
        cell.setStatus(Cell.cells.empty);
        Arrays.fill(matrix, cell);
        weight = 10;
        height = 24;
    }

    public void copy(MyField field) {
        for (int i = 0; i < 240; i++) {
            matrix[i] = field.getMatrix()[i];
        }
        weight = field.getWeight();
        height = field.getHeight();
    }

    public Cell getCell(int index) {
        return matrix[index];
    }

    public int getWeight() {
        return weight;
    }

    public int getHeight() {
        return height;
    }

    public Cell[] getMatrix() {
        return matrix;
    }

    public void doEmpty() {
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < weight; j++) {
                Cell cell = new Cell();
                cell.setStatus(Cell.cells.empty);
                matrix[i*weight + j] = cell;
            }
        }
    }

    void removeLine(int index) {
        Cell[] newMatrix = new Cell[240];
        int newRow = height - 1;

        for (int i = height - 1; i >= 0; i--) {
            if (i == index) {
                continue; // пропускаем удаляемую строку
            }
            for (int j = 0; j < weight; j++) {
                newMatrix[newRow * weight + j] = matrix[i * weight + j];
            }
            newRow--;
        }

        for (int i = 0; i < weight; i++) {
            Cell emptyCell = new Cell();
            emptyCell.setStatus(Cell.cells.empty);
            newMatrix[i] = emptyCell;
        }

        matrix = newMatrix;
    }
}
