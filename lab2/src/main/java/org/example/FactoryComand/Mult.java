package org.example.FactoryComand;

import java.util.EmptyStackException;
import java.util.Stack;

public class Mult implements iComand {
    public void calc(Stack<Double> stack) {
        if (stack.size() < 2) {
            throw new EmptyStackException();
        }
        Double first = stack.pop();
        Double second = stack.pop();
        stack.push(first*second);
    }
}
