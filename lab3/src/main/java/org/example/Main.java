package org.example;

import org.example.Controller.Controller;

import javax.swing.*;



public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
        });

    }
}

