package org.example.FactoryComand;

import org.example.Calculator.Context;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Stack;

public class FactoryTest {
    private Factory factory;
    private Context context;
    @BeforeEach
    void set_object() {
        factory = new Factory();
        context = new Context(new Stack<Double>(), new HashMap<>());
        factory.register_creator("+",  new Plus(context));
        factory.register_creator("/",  new Div(context));
        factory.register_creator("*",  new Mult(context));
        factory.register_creator("-",  new Minus(context));
        factory.register_creator("SQRT",  new Sqrt(context));
    }
    @Test
    void calc_test_exception() {
        context.get_stack().push(-1.0);
        factory.create_and_calc("sqrt");
        Assertions.assertEquals(-1, context.get_stack().pop());
    }
    @Test
    void calc_test_right() {
        context.get_stack().push(-1.0);
        context.get_stack().push(-1.0);
        factory.create_and_calc("+");
        Assertions.assertEquals(-2, context.get_stack().pop());
    }
}
