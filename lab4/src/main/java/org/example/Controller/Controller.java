package org.example.Controller;

import org.example.Factory.Factory;

import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class Controller extends WindowAdapter {
    private final Factory factory;
    public Controller(Factory factory) {
        this.factory = factory;
    }

    @Override
    public void windowClosing(WindowEvent e) {
        factory.stopWorking();
    }
}
