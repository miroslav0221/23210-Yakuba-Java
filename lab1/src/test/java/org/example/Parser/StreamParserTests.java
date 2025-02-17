package org.example.Parser;

import org.example.Parser.StreamParser;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class StreamParserTests {
    private StreamParser parser;
    @BeforeEach
    void set_object() {
        parser = new StreamParser();
    }
    @Test
    void parse_normal_argument_test() {
        String[] args = {"test.txt"};
        parser.parse_argument(args);
        StringBuilder word = parser.next_word();
        Assertions.assertNotNull(word);

    }
    @Test
    void parse_invalid_argument_test1() {
        String[] args = {};
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            parser.parse_argument(args);});

    }
    @Test
    void parse_invalid_argument_test2() {
        String[] args = {"nofile.txt"};
        Assertions.assertThrows(IllegalArgumentException.class, () -> {
            parser.parse_argument(args);});

    }

    @Test
    void next_word_correct_test() {
        String[] args = {"test.txt"};
        parser.parse_argument(args);
        Assertions.assertEquals("word1", parser.next_word().toString());
        Assertions.assertEquals("word2", parser.next_word().toString());
        Assertions.assertEquals("word3", parser.next_word().toString());
        Assertions.assertEquals("word4", parser.next_word().toString());

    }
}
