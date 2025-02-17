package org.example.StatWords;

import org.example.StatWords.Counter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class CounterTests {
    private Counter counter;

    @BeforeEach
    void set_object() {
        counter = new Counter();
    }
    @Test
    void get_count_default() {
        int count = counter.get_count();
        Assertions.assertEquals(1, count);
    }
    @Test
    void add_test() {
        counter.add();
        counter.add();
        counter.add();
        Assertions.assertEquals(4, counter.get_count());
    }
}
