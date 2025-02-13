package FactoryComand;

import java.util.EmptyStackException;
import java.util.Stack;

public class Plus implements iComand{
    public void calc(Stack<Double> stack) {
        if (stack.size() < 2) {
            throw new EmptyStackException();
        }
        Double first = stack.pop();
        Double second = stack.pop();
        //проверить на переполнение
        stack.push(first+second);
    }
}
