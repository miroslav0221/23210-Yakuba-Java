package org.example.FactoryComand;
import java.util.EmptyStackException;
import java.util.Stack;

public class Div implements iComand {
    @Override
    public void calc(Stack<Double> stack) {
        if (stack.size() < 2) {
            throw new EmptyStackException();
        }
        Double divider = stack.pop(); // делитель
        Double dividend = stack.pop(); // делимое
        if (divider == 0.0) {
            throw new ArithmeticException();
        }
        stack.push(dividend/divider);
    }
}

