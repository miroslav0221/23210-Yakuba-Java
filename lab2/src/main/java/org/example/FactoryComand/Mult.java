package org.example.FactoryComand;

import org.example.Calculator.Context;

public class Mult extends iComand {
    private final Context context;
    public Mult(Context context_) {
        super(context_);
        context = context_;
    }

    @Override
    public void calc() throws Exception {
        if (context.get_stack().size() < 2) {
            comandLogger.debug("Не достаточно элементов в стеке при произведении");
            throw new Exception("Не хватает элементов в стеке");
        }
        Double first = context.get_stack().pop();
        Double second = context.get_stack().pop();
        context.get_stack().push(first*second);
    }
}
