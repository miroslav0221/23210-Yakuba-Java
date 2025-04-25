package org.example.Calculator;

import org.example.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.*;

public class CalcularotrTest {
    private Calculator calculator;

    @BeforeEach
    void set_object() {
        calculator = new Calculator();
    }
    @Test
    void console_mode_test() throws IOException {
        calculator = new Calculator();
        String[] args = {};
        calculator.create_parser(args);
        System.setIn(new FileInputStream("file.txt"));
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        calculator.run();
        System.setOut(originalOut);
        Assertions.assertEquals("###########################################################\n" +
                "Возможности калькулятора:\n" +
                "print - распечатать верхний элемент стека.\n" +
                "pop - удалить верхний элемент стека.\n" +
                "define - создать макрос.\n" +
                "- - разница 2 верхних элементов(из нижнего вычитаем верхний).\n" +
                "+ - сумма 2 верхних элементов.\n" +
                "/ - деление 2 верхних элементов(нижний делим на верхний).\n" +
                "sqrt - извлечение квадратного корня из верхнего элемента\n" +
                "* - умножение 2 верхних элементов.\n" +
                "exit - завершить работку калькулятора.\n" +
                "############################################################\n" +
                "Калькулятор:\n" +
                "200.0", outputStream.toString().trim());
    }
    @Test
    void file_mode_test() {
        String[] args = {"TestMain"};
        calculator.create_parser(args);
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        calculator.run();
        System.setOut(originalOut);
        Assertions.assertEquals("200.0", outputStream.toString().trim());
    }
}
