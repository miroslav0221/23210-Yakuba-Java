package org.example.Model;

import java.io.*;
import java.util.List;

public class Parser {
    BufferedReader createReader() {
        try
        {
            BufferedReader reader = new BufferedReader(new FileReader("Figures.txt"));
            return reader;
        }
        catch (IOException e)
        {
            System.err.println(e.getLocalizedMessage());
        }
        return null;
    }

    void parse(List<Figure> listFigure) {
        BufferedReader reader = createReader();

        if (reader == null) {
            return;
        }
        String colour = "";
        try {
            String line;
            Figure figure;
            figure = new Figure();
            while ((line = reader.readLine()) != null) {
                if (line.isEmpty()) {
                    continue;
                }
                if (Character.isLetter(line.charAt(0))) {
                    figure.setColour(line);
                    colour = line;
                    continue;
                }
                else {
                    Cell[] matrix = new Cell[4*4];
                    int value;
                    for (int j = 0; j < 4; j++) {
                        for (int i = 0; i < 4; i++) {
                            value = Character.getNumericValue(line.charAt(i));
                            Cell cell = new Cell();
                            if (value == 1) {
                                cell.setStatus(Cell.cells.filled);
                                cell.setColour(figure.getColour());
                                matrix[j*4 + i] = cell;
                            }

                            if (value == 0) {
                                cell.setStatus(Cell.cells.empty);
                                cell.setColour("none");
                                matrix[j*4 + i] = cell;
                            }
                        }
                        line = reader.readLine();
                    }
                    figure.setMatrix(matrix);
                    //figure.setColour(colour);
                    listFigure.add(figure);
                    figure = new Figure();
                }
            }

        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

}
