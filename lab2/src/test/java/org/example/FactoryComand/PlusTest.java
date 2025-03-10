package org.example.FactoryComand;

import org.example.Calculator.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Stack;

public class PlusTest {
    private Factory factory;
    private Context context;
    @BeforeEach
    void set_object() {
        factory = new Factory();
        factory = new Factory();
        context = new Context(new Stack<Double>(), new HashMap<>());
        factory.register_creator("+",  new Plus(context));
        factory.register_creator("/",  new Div(context));
        factory.register_creator("*",  new Mult(context));
        factory.register_creator("-",  new Minus(context));
        factory.register_creator("SQRT",  new Sqrt(context));
    }
    @Test
    void plus_test() {
        context.get_stack().push(9.0);
        context.get_stack().push(3.0);
        factory.create_and_calc("+");
        Assertions.assertEquals(12.0, context.get_stack().peek());
    }

    @Test
    void plus_empty_stack_test() {
        factory.create_and_calc("+");
        Assertions.assertTrue(context.get_stack().isEmpty());
    }
}
