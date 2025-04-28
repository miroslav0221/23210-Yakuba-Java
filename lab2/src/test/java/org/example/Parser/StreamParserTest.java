package org.example.Parser;

import org.example.Calculator.Context;
import org.example.Main;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.HashMap;
import java.util.Stack;

public class StreamParserTest {
    private StreamParser parser1;
    private StreamParser parser2;
    private StreamParser parser3;
    private Context context;
    @BeforeEach
    void set_object() {
        String[] args1 = {"testfile", "string"};
        String[] args2 = {};
        String[] args3 = {"fileinvalid"};
        context = new Context(new Stack<Double>(), new HashMap<>());
        parser1 = new StreamParser(args1, context);
        parser2 = new StreamParser(args2, context);
        parser3 = new StreamParser(args3, context);
    }
    @Test
    void parse_invalid_argument_test_1() {
        Assertions.assertEquals(1, parser2.parse_argument());
    }
    @Test
    void parse_correct_argument_test() {
        Assertions.assertEquals(0, parser1.parse_argument());
    }
    @Test
    void parse_invalid_argument_test_2() {
        Assertions.assertEquals(1, parser3.parse_argument());
    }
    @Test
    void next_operation_test() {
        parser1.parse_argument();
        parser1.set_mode("file");
        parser1.next_operation();
        Assertions.assertTrue(context.get_stack().isEmpty());
        parser1.next_operation();
        Assertions.assertTrue(context.get_stack().isEmpty());
        parser1.next_operation();
        Assertions.assertTrue(context.get_stack().isEmpty());
        Assertions.assertEquals("AAAAAAAA", parser1.next_operation());
        parser1.next_operation();
        Assertions.assertTrue(context.get_stack().isEmpty());
        parser1.next_operation();
        Assertions.assertTrue(context.get_stack().isEmpty());
        parser1.next_operation();
        Assertions.assertEquals(13, context.get_stack().peek());
        parser1.next_operation();
        Assertions.assertEquals(100, context.get_stack().peek());
        parser1.next_operation();
        Assertions.assertEquals(13, context.get_stack().peek());
        parser1.next_operation();
        Assertions.assertTrue(context.get_stack().isEmpty());
        Assertions.assertEquals("", parser1.next_operation());
        Assertions.assertEquals("", parser1.next_operation());
        parser1.next_operation();
        Assertions.assertEquals(100, context.get_stack().peek());



        Assertions.assertEquals("", parser1.next_operation());

        Assertions.assertEquals("SQRT", parser1.next_operation());
        Assertions.assertNull(parser1.next_operation());
    }
}
