package org.example.FactoryComand;

import org.example.Calculator.Context;

import java.util.EmptyStackException;

public class Sqrt extends iComand {
    private final Context context;
    public Sqrt(Context context_) {
        super(context_);
        context = context_;
    }
    @Override
    public void calc() throws Exception {
        if (context.get_stack().isEmpty()) {
            comandLogger.debug("Не достаточно элементов в стеке при извлечении " +
                    "квадратного корня");
            throw new EmptyStackException();
        }
        Double val = context.get_stack().pop();
        if (val < 0.0) {
            comandLogger.debug("Извлечение квадратного корня из отрицательного числа");

            throw new Exception("отрицательное число");
        }
        context.get_stack().push(Math.sqrt(val));
    }
}
