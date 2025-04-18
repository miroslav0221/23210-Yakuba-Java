//package org.example.Controller;
//import org.example.Model.Figure;
//import org.example.Model.FigureFactory;
//import org.example.Model.Model;
//import org.example.Model.MyField;
//import org.example.View.View;
//import org.example.View.GameListener;
//import javax.swing.*;
//import java.awt.*;
//import java.awt.event.KeyEvent;
//import java.awt.event.KeyListener;
//import java.lang.reflect.Field;
//import java.util.concurrent.atomic.AtomicInteger;
//
//import static java.lang.Thread.sleep;
//
//public class Controller {
//
//    private volatile boolean running = false;
//    private String[] tableScores;
//    private Model model;
//    private View view;
//    int scores = 0;
//
//
//    public Controller() {
//        model = new Model();
//        view = new View(model.getField(), model, startGame());
//        view.showWindow();
//    }
//
//    private void initTablesRecords() {
//        for (int i = 0; i < 5; i++) {
//            tableScores[i] = "0";
//        }
//    }
//
//    private void addRecord(int record) {
//        boolean flag = false;
//        String valueCur = "0";
//        for (int i = 0; i < 5; i++) {
//            if (record > Integer.parseInt(tableScores[i]) && !flag) {
//                valueCur = tableScores[i];
//                tableScores[i] = String.valueOf(record);
//                flag = true;
//                continue;
//            }
//            if (flag) {
//                if (i < 4) {
//                    String valueSave = tableScores[i];
//                    tableScores[i] = valueCur;
//                    valueCur = valueSave;
//                }
//                else {
//                    tableScores[i] = valueCur;
//                }
//            }
//
//        }
//        scores = 0;
//    }
//
//    public GameListener startGame() {
//        GameListener listener = new GameListener() {
//            @Override
//            public void replay() throws InterruptedException {
//                model.getField().doEmpty();
//            }
//
//            @Override
//            public void exit() {
//                System.exit(0);
//            }
//
//            @Override
//            public void play() throws InterruptedException {
//                Controller.this.play();
//            }
//        };
//        return listener;
//    }
//    public void play() throws InterruptedException {
//        //view.initView();
//        tableScores = new String[5];
//        initTablesRecords();
//        FigureFactory factory = new FigureFactory();
//
//        factory.initListFigure();
//
//
//        while (true) {
//            int countOffset = 0;
//            Figure figure = factory.createRandomFigure();
//            while(true) {
//                view.printGame(figure);
//                sleep(500);
//                if(!Model.down(figure, model.getField())) {
//                    System.out.println(figure.getY());
//                    model.mergeFigureToField(figure, model.getField());
//                    int index = model.checkFillRow();
//                    int count = 0;
//                    while(index != -1) {
//                        model.removeLine(index);
//                        if (count == 0) {
//                            scores += 100;
//                        }
//                        if (count == 1) {
//                            scores += 300;
//                        }
//                        if (count == 2) {
//                            scores += 700;
//                        }
//                        if (count == 3) {
//                            scores += 1500;
//                        }
//                        index = model.checkFillRow();
//                        count++;
//                        view.updateScore(scores);
//                    }
//                    break;
//                };
//
//
//                if (figure.getY() > 2) {
//                    countOffset++;
//                }
//
//            }
//            if (countOffset == 0) {
//
////                view.setListener(new GameListener() {
////
////                    @Override
////                    public void replay() throws InterruptedException {
////                        model.getField().doEmpty();
////                    }
////
////                    @Override
////                    public void exit() {
////                        System.exit(0);
////                    }
////                });
//
//                view.updateScore(0);
//                addRecord(scores);
//                System.out.println("-------------");
//                view.updateTable(tableScores);
//                System.out.println("-------------");
//                view.GameOver();
//            }
//        }
//
//
//    }
//
//}

package org.example.Controller;

import org.example.Model.*;
import org.example.View.GameListener;
import org.example.View.View;

import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;

public class Controller implements GameListener {
    private int countPauseTap = 0;
    private final Model model;
    private final View view;
    private int scores;
    private String[] tableScores;
    private volatile boolean running = false;
    private Thread time;
    private int countTimer = 0;

    int seconds = 0;

    public Controller() {
        model = new Model();
        scores = 0;
        tableScores = new String[20];
        initTablesRecords();
        view = new View(model.getField(), model, this);
        view.showWindow();
    }

    @Override
    public void play() throws InterruptedException {
//        if (countPauseTap % 2 == 1) {
//            countPauseTap++;
//        }
        FigureFactory factory = new FigureFactory();
        factory.initListFigure();

        running = true;
        if (countTimer == 0) {
            time = new Thread(() -> {
                while (running) {
                    try {
                        while(countPauseTap % 2 == 1) {
                            Thread.sleep(1);
                        }
                        Thread.sleep(1000);
                    }
                    catch (InterruptedException e) {
                        break;
                    }
                    seconds++;
                    int mins = seconds / 60;
                    int secs = seconds % 60;
                    String formatted = String.format("%02d:%02d", mins, secs);
                    view.updateTimer(formatted);
                }
            });
            time.start();
            countTimer++;
        }

        while (running) {
            int countOffset = 0;
            Figure figure = factory.createRandomFigure();
            view.updateFigure(figure);
            while (running) {
                while (countPauseTap % 2 == 1) {
                    Thread.sleep(1);
                }

                view.printGame(figure);
                Thread.sleep(500);

                if (!Model.down(figure, model.getField())) {
                    model.mergeFigureToField(figure, model.getField());
                    int index = model.checkFillRow();
                    int count = 0;

                    while (index != -1) {
                        model.removeLine(index);
                        if (count == 0) scores += 100;
                        if (count == 1) scores += 300;
                        if (count == 2) scores += 700;
                        if (count == 3) scores += 1500;
                        index = model.checkFillRow();
                        count++;
                        view.updateScore(scores);
                    }
                    break;
                }

                if (figure.getY() > 2) {
                    countOffset++;
                }
            }

            if (!running) break;

            if (countOffset == 0) {
                view.updateScore(0);
                addRecord(scores);
                view.updateTable(tableScores);
                view.updateGlobalTableRecords(tableScores);
                seconds = 0;
                countPauseTap++;
                view.GameOver();
                break;
            }
        }
    }

    @Override
    public void replay() throws InterruptedException {
        model.getField().doEmpty();
        scores = 0;
        view.updateScore(0);
    }

    @Override
    public void stop() {
        if (countPauseTap % 2 == 1) {
            countPauseTap++;
        }
        seconds = 0;
        running = false;
    }

    @Override
    public void pause() {
        countPauseTap++;
    }

    @Override
    public void resetScores() {
        view.updateScore(0);
        addRecord(scores);
        view.updateTable(tableScores);
        view.updateGlobalTableRecords(tableScores);
        scores = 0;
    }

    @Override
    public void exit() {
        System.exit(0);
    }

    private void initTablesRecords() {
        for (int i = 0; i < 20; i++) {
            tableScores[i] = "0";
        }
    }

    private void addRecord(int record) {
        boolean flag = false;
        String valueCur = "0";
        for (int i = 0; i < 20; i++) {
            if (record > Integer.parseInt(tableScores[i]) && !flag) {
                valueCur = tableScores[i];
                tableScores[i] = String.valueOf(record);
                flag = true;
                continue;
            }
            if (flag) {
                if (i < 4) {
                    String valueSave = tableScores[i];
                    tableScores[i] = valueCur;
                    valueCur = valueSave;
                }
                else {
                    tableScores[i] = valueCur;
                }
            }

        }
        scores = 0;
    }
}

