package org.example;
import org.example.Calculator.Calculator;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.*;


public class Main {
    static final Logger mainLogger = LogManager.getLogger(Main.class.getName());
    public static void main(String[] args) {
        Calculator calculator = new Calculator();
        calculator.create_parser(args);
        mainLogger.debug("Был создан парсер");
        calculator.run();
        mainLogger.info("Калькулятор завершил работу");
    }


}