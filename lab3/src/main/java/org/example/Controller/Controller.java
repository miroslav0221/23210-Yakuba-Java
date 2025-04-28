package org.example.Controller;

import org.example.Model.*;
import org.example.View.GameListener;
import org.example.View.View;

public class Controller implements GameListener {
    private final Model model;
    private final View view;
    private int scores;
    private String[] tableScores;

    private volatile boolean running = false;
    private volatile boolean paused = false;
    private int maxTime = 700;
    private int minTime = 300;
    private int curTime = maxTime;
    private Thread time;
    private int seconds = 0;

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
        paused = false;
        running = true;
        seconds = 0;

        FigureFactory factory = new FigureFactory();
        factory.initListFigure();

        time = new Thread(() -> {
            while (running) {
                try {
                    while (paused) {
                        Thread.sleep(100);
                    }
                    Thread.sleep(1000);
                }
                catch (InterruptedException e) {
                    return;
                }

                seconds++;
                int mins = seconds / 60;
                int secs = seconds % 60;
                String formatted = String.format("%02d:%02d", mins, secs);
                view.updateTimer(formatted);
            }
        });
        time.start();

        while (running) {
            int countOffset = 0;
            Figure figure = factory.createRandomFigure();
            view.updateFigure(figure);

            while (running) {
                while (paused) {
                    Thread.sleep(100);
                }

                view.printGame(figure);


                Thread.sleep(curTime);

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
                paused = true;
                if (time != null && time.isAlive()) {
                    time.interrupt();
                }
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
        seconds = 0;
        paused = false;
        running = true;
    }

    @Override
    public void stop() {
        paused = false;
        running = false;
        seconds = 0;

        if (time != null && time.isAlive()) {
            time.interrupt();
        }
    }

    @Override
    public void pause() {
        paused = !paused;
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

    public void addRecord(int record) {
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
                if (i < 19) {
                    String valueSave = tableScores[i];
                    tableScores[i] = valueCur;
                    valueCur = valueSave;
                } else {
                    tableScores[i] = valueCur;
                }
            }
        }
        scores = 0;
    }
    public int getScores() {
        return scores;
    }

    public String[] getTableScores() {
        return tableScores;
    }

    public boolean isRunning() {
        return running;
    }

    public boolean isPaused() {
        return paused;
    }

}
