package org.example.View;

public interface GameListener {
    void replay() throws InterruptedException;
    void exit();
    void play() throws InterruptedException;
    void stop();
    void resetScores();
    void pause();
}
