package org.example;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Supplier;

import org.example.FactoryComand.*;


public class Calculator {

    private final Stack<Double> stack;
    private final StreamParser parser;
    private final Factory<iComand> factory;
    private final Map<String, Double> map_var;

    Calculator(StreamParser parser_) {
        parser = parser_;
        stack = new Stack<Double>();
        factory = new Factory<iComand>();
        map_var = new HashMap<>();
    }

    void create_factory() {
        factory.register_creator("+",  new Creator<>(Plus::new));
        factory.register_creator("/",  new Creator<>(Div::new));
        factory.register_creator("*",  new Creator<>(Mult::new));
        factory.register_creator("-",  new Creator<>(Minus::new));
        factory.register_creator("SQRT",  new Creator<>(Sqrt::new));
    }
    void process_comand(String operation_str) {
        if (operation_str.isEmpty()) {
            return;
        }
        iComand operation = factory.create(operation_str);
        operation.calc(stack);
    }

    void file_mode() {
        String operation_str = parser.next_operation(map_var, stack, "file");
        while (operation_str != null) {
            process_comand(operation_str);
            operation_str = parser.next_operation(map_var, stack, "file");
        }
    }
    void console_mode() {
        System.out.println("Калькулятор:");
        String operation_str = parser.next_operation(map_var, stack, "console");
        while (!operation_str.equals("EXIT")) {
            process_comand(operation_str);
            operation_str = parser.next_operation(map_var, stack, "console");
        }


    }
    public void run() {
        create_factory();
        if (parser.parse_argument() == 0) {
            file_mode();
        }
        else {
            console_mode();
        }
    }



}
