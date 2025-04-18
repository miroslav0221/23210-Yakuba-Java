package org.example.Calculator;
import java.util.HashMap;
import java.util.Stack;

public class Context {
    private final Stack<Double> stack;
    private final HashMap<String, Double> map_val;
    public Context(Stack<Double> stack_, HashMap<String, Double> map_val_) {
        stack = stack_;
        map_val = map_val_;
    }
    public Stack<Double> get_stack() {
        return stack;
    }
    public HashMap<String, Double> get_map() {
        return map_val;
    }
}
