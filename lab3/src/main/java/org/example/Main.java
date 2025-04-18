package org.example;

import org.example.Controller.Controller;

import javax.swing.*;

//public class Main {
//    public static void main(String[] args) {
//        // Создаём окно
//        JFrame frame = new JFrame("Тетрис");
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.setSize(600, 1000);
//
//        // Добавляем надпись
//        JLabel label = new JLabel("Привет, Swing!", JLabel.CENTER);
//        frame.add(label);
//
//        // Отображаем окно
//        frame.setVisible(true);
//    }

public class Main {
    public static void main(String[] args) {

        SwingUtilities.invokeLater(() -> {
            Controller controller = new Controller();
        });

    }
}

