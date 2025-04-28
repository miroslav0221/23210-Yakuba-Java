package org.example.View;

import org.example.Model.Cell;
import org.example.Model.Figure;
import org.example.Model.Model;
import org.example.Model.MyField;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class TetrisPanel extends JPanel {
    MyField field;
    Figure figure;
    final int sizeCell = 33;

    TetrisPanel() {
        figure = null;
        field = null;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        drawField(g);
    }

    @Override
    public Dimension getPreferredSize() {
        if (field != null) {
            return new Dimension(field.getWeight() * sizeCell, field.getHeight() * sizeCell);
        }
        else {
            // запасной размер на случай, если данных пока нет
            return new Dimension(333, 1000);
        }
    }

    public void updateData(MyField field_, Figure figure_) {
        field = field_;
        figure = figure_;
    }


    private void drawField(Graphics g) {
        for (int row = 0; row < field.getHeight(); row++) {
            for (int col = 0; col < field.getWeight(); col++) {
                Cell cell = field.getCell(row*field.getWeight() + col);
                if (cell.getStatus() == Cell.cells.filled) {
                    System.out.println(cell.getColour());
                    switch (cell.getColour()) {
                        case "black":
                            Color colorBlack = new Color(0,0,0,  70);
                            g.setColor(colorBlack);
                            break;

                        case "blue":
                            g.setColor(Color.BLUE);
                            break;

                        case "red":
                            g.setColor(Color.RED);
                            break;

                        case "yellow":
                            g.setColor(Color.YELLOW);
                            break;

                        case "pink":
                            g.setColor(Color.PINK);
                            break;

                        case "green":
                            g.setColor(Color.GREEN);
                            break;

                        case "orange":
                            g.setColor(Color.ORANGE);
                            break;

                        case "purple":
                            g.setColor(Color.MAGENTA);
                            break;

                    }
                    g.fillRect(col * sizeCell, row * sizeCell, sizeCell, sizeCell);

                    g.setColor(Color.BLACK);

                    g.drawRect(col * sizeCell, row * sizeCell, sizeCell, sizeCell);
                }
                else {
                    if (row < 4) {
                        Color colorGray = new Color(128, 128, 128,  70);

                        g.setColor(colorGray);
                        g.fillRect(col * sizeCell, row * sizeCell, sizeCell, sizeCell);

                    }
                    else {
                        Color colorPink = new Color(180, 126, 222,  70);
                        g.setColor(colorPink);
                        g.fillRect(col * sizeCell, row * sizeCell, sizeCell, sizeCell);

                    }
                    g.setColor(Color.BLACK);
                    g.drawRect(col * sizeCell, row * sizeCell, sizeCell, sizeCell);
                }
            }
        }
    }

}
