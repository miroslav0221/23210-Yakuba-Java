package org.example;

import java.util.Scanner;
import java.util.Stack;

public class Main {

    public static void main(String[] args) {
        StreamParser parser = new StreamParser(args);
        Calculator calculator = new Calculator(parser);
        calculator.run();

    }


}