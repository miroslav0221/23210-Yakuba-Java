package org.example.Calculator;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.Stack;

public class ContextTest {
    private Stack<Double> stack;
    private HashMap<String, Double> map;
    @BeforeEach
    void set_objects() {
        stack = new Stack<>();
        map = new HashMap<>();
    }
    @Test
    void get_map_test() {
        Context context = new Context(stack, map);
        Assertions.assertEquals(map.hashCode(), context.get_map().hashCode());
    }
    @Test
    void get_stack_test() {
        Context context = new Context(stack, map);
        Assertions.assertEquals(stack.hashCode(), context.get_stack().hashCode());
    }
}
