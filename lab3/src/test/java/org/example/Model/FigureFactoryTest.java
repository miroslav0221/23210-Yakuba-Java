package org.example.Model;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.List;

public class FigureFactoryTest {
    private FigureFactory figureFactory;

    @BeforeEach
    void setObject() {
        figureFactory = new FigureFactory();
    }

    @Test
    public void initListFigureTest() {
        figureFactory.initListFigure();
        List<Figure> list = figureFactory.getListFigure();
        Assertions.assertEquals(7, list.size());
    }

    @Test
    public void createRandomFigureTest() {
        figureFactory.initListFigure();
        Figure figure = figureFactory.createRandomFigure();
        boolean flag = false;
        switch (figure.getColour()) {
            case "blue":
                flag = true;
                break;
            case "red":
                flag = true;
                break;
            case "purple":
                flag = true;
                break;
            case "green":
                flag = true;
                break;
            case "orange":
                flag = true;
                break;
            case "pink":
                flag = true;
                break;
            case "yellow":
                flag = true;
                break;

        }
        Assertions.assertTrue(flag);
    }
}
