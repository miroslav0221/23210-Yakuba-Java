package org.example.Calculator;

import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Stack;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.FactoryComand.*;
import org.example.Main;
import org.example.Parser.StreamParser;


public class Calculator {
    static final Logger calculatorLogger = LogManager.getLogger(Calculator.class.getName());
    private final Context context;
    private StreamParser parser;
    private final Factory factory;

    public Calculator() {
        context = new Context(new Stack<Double>(), new HashMap<>());
        factory = new Factory();

    }

    void create_factory() {
        factory.register_creator("+",  new Plus(context));
        factory.register_creator("/",  new Div(context));
        factory.register_creator("*",  new Mult(context));
        factory.register_creator("-",  new Minus(context));
        factory.register_creator("SQRT",  new Sqrt(context));
    }
    void process_command(String operation_str) {
        if (operation_str.isEmpty()) {
            return;
        }
        factory.create_and_calc(operation_str);
    }
    void help() {
        Path path = Paths.get("help.txt");
        try {
            String content = Files.readString(path);
            System.out.println(content);
        }
        catch (Exception e) {
            calculatorLogger.error("Не удалось открыть файл с информацией о командой");
            System.err.println(e.getLocalizedMessage());
        }
    }

    void file_mode() {
        String operation_str = parser.next_operation();
        calculatorLogger.debug("Получена операция файла");
        while (operation_str != null) {
            process_command(operation_str);
            calculatorLogger.debug("Обработана операция файла");
            operation_str = parser.next_operation();
            calculatorLogger.debug("Получена операция файла");
        }
    }
    void console_mode() {
        help();
        calculatorLogger.debug("Напечатана информация о командах");
        calculatorLogger.info("Калькулятор запущен");
        System.out.println("Калькулятор:");
        String operation_str = parser.next_operation();
        calculatorLogger.debug("Получена операция");
        while (!operation_str.equals("EXIT")) {
            process_command(operation_str);
            calculatorLogger.debug("Обработана операция");
            operation_str = parser.next_operation();
            calculatorLogger.debug("Получена операция");
        }
    }
    public void create_parser(String[] args) {
        parser = new StreamParser(args, context);
    }

    public void run() {
        create_factory();
        calculatorLogger.debug("Была создана фабрика");
        if (parser.parse_argument() == 0) {
            parser.set_mode("file");
            calculatorLogger.debug("Установлен режим file");
            file_mode();
        }
        else {
            parser.set_mode("console");
            calculatorLogger.debug("Установлен режим console");
            console_mode();
        }
    }


}
