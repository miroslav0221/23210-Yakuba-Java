package org.example.Model;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class FigureFactory {
    private List<Figure> listFigure;


    public void initListFigure() {
        listFigure = new ArrayList<>();
        Parser parser = new Parser();
        parser.parse(listFigure);

    }

    public Figure createRandomFigure() {
        initListFigure();
        Random rand = new Random();
        int index = Math.abs(rand.nextInt() % 7);
        return listFigure.get(index);
    }
}
