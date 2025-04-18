package org.example.Model;

import java.lang.reflect.Field;

public class Model {
    private MyField field;

    public Model() {
        field = new MyField();
    }

    public void setField(MyField fieldNew) {
        field = fieldNew;
    }


    public void offsetLimitPosition(Figure figureShadow, MyField fieldCur) {
        while (true) {
            if (!down(figureShadow, fieldCur)) {
                break;
            }
        }

    }

    public void mergeFigureToField(Figure figure, MyField fieldCur) {
        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {
                if (figure.getCell(i*4 + j).getStatus() == Cell.cells.filled) {
                    int indexX = figure.getX() + j;
                    int indexY = figure.getY() + i;

                    if (indexX >= 0 && indexX < fieldCur.getWeight() && indexY >= 0 && indexY < fieldCur.getHeight()) {
                        fieldCur.getMatrix()[indexY*fieldCur.getWeight() + indexX] = figure.getCell(i*4 + j);
                    }
                }
            }
        }
    }

    static public boolean checkPositionFigure(Figure figure, MyField fieldCur, int newX, int newY) {

        for (int i = 0; i < 4; i++) {
            for (int j = 0; j < 4; j++) {

                if (figure.getCell(i*4 + j).getStatus() == Cell.cells.empty) {
                    continue;
                }

                int indexX = newX + j;
                int indexY = newY + i;
                if (indexX >= fieldCur.getWeight() || indexX < 0 || indexY < 0
                        || indexY >= fieldCur.getHeight())
                {
                    return false;
                }

                if (fieldCur.getCell(indexY*fieldCur.getWeight() + indexX).getStatus() == Cell.cells.filled) {
                    return false;
                }

            }
        }
        return true;
    }

    public int checkFillRow() {

        int countCells = 0;
        for (int i = field.getHeight() - 1; i > 0; i--) {
            for (int j = 0; j < field.getWeight(); j++) {
                if (field.getCell(i*field.getWeight() + j).getStatus()
                        == Cell.cells.filled) {
                    countCells++;
                }
                else {
                    countCells = 0;
                    break;
                }
            }
            if (countCells == 10) {
                return i;
            }

        }
        return -1;
    }

    public void removeLine(int index) {
        field.removeLine(index);
    }

    static public boolean down(Figure figure, MyField fieldCur) {
        if (checkPositionFigure(figure, fieldCur, figure.getX(), figure.getY() + 1)) {
            figure.incrY();
            return true;
        }
        return false;
    }

    static public void right(Figure figure, MyField fieldCur) {
        if(checkPositionFigure(figure, fieldCur, figure.getX() + 1, figure.getY())) {
            figure.incrX();
        }
    }

    static public void left(Figure figure, MyField fieldCur) {
        if(checkPositionFigure(figure, fieldCur,figure.getX() - 1, figure.getY())) {
            figure.dicrX();
        }
    }

    static public void rotate(Figure figure, MyField field) {
        Figure figureCopy = new Figure();
        figureCopy.copy(figure);
        figureCopy.rotate();
        if(checkPositionFigure(figureCopy, field, figure.getX(), figure.getY())) {
            figure.copy(figureCopy);
        }
    }

    public MyField getField() {
        return field;
    }
}
