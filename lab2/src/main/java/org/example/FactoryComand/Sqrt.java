package org.example.FactoryComand;

import java.util.EmptyStackException;
import java.util.Stack;

public class Sqrt implements iComand{
    public void calc(Stack<Double> stack) {
        if (stack.isEmpty()) {
            throw new EmptyStackException();
        }
        Double val = stack.pop();
        if (val < 0.0) {
            throw new ArithmeticException();
        }
        stack.push(Math.sqrt(val));
    }
}
