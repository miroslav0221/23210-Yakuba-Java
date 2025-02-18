package org.example;

import java.util.Stack;
import java.util.function.Supplier;

import org.example.FactoryComand.*;


public class Calculator {

    Stack<Double> stack;
    String name_file_;
    Factory<iComand> factory;

    Calculator(String name_file) {
        name_file_ = name_file;
        stack = new Stack<Double>();
        factory = new Factory<iComand>();
    }

    void create_factory() {
        factory.register_creator("PLUS",  new Creator<>(Plus::new));
        factory.register_creator("DIV",  new Creator<>(Div::new));
        factory.register_creator("MULT",  new Creator<>(Mult::new));
        factory.register_creator("MINUS",  new Creator<>(Minus::new));
        factory.register_creator("PRINT",  new Creator<>(Print::new));
        factory.register_creator("SQRT",  new Creator<>(Sqrt::new));
        factory.register_creator("PUSH",  new Creator<>(Push::new));
        factory.register_creator("POP",  new Creator<>(Pop::new));
        factory.register_creator("DEFINE",  new Creator<>(Define::new));
    }

    public void run() {
        create_factory();

    }

    void init_stack() {

    }

    void process_file() {

    }

}
