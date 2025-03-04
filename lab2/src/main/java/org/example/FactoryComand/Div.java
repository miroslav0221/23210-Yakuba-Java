package org.example.FactoryComand;
import org.example.Calculator.Context;

import java.util.EmptyStackException;
import java.util.Stack;

public class Div extends iComand {
    private final Context context_;

    public Div(Context context) {
        super(context);
        context_ = context;
    }

    @Override
    public void calc() {
        Stack<Double> stack = context_.get_stack();
        if (stack.size() < 2) {
            comandLogger.debug("Не достаточно элементов в стеке при делении");
            throw new EmptyStackException();
        }
        Double divider = stack.pop(); // делитель
        Double dividend = stack.pop(); // делимое
        if (divider == 0.0) {
            stack.push(dividend);
            stack.push(divider);
            comandLogger.error("Деление на ноль");
            throw new ArithmeticException("деление на ноль");
        }
        stack.push(dividend/divider);
    }
}

