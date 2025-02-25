package org.example.Report;

import org.example.StatWords.Counter;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ReportGeneratorTests {
    private ReportGenerator generator;
    @BeforeEach
    void set_object() {
        generator = new ReportGenerator();
    }
    @Test
    void map_to_list_test() {
        Map<String, Counter> map_ = new HashMap<>();
        Counter counter1 = new Counter();
        counter1.add();
        counter1.add();
        counter1.add();
        map_.put("word1", counter1);
        Counter counter2 = new Counter();
        counter2.add();
        counter2.add();
        counter2.add();
        counter2.add();
        map_.put("word2", counter2);
        Counter counter3 = new Counter();
        counter3.add();
        counter3.add();
        counter3.add();
        counter3.add();
        counter3.add();
        map_.put("word3", counter3);
        generator.map_to_list(map_, 12);
        List<ReportRow> list;
        list = generator.get_list();
        Assertions.assertEquals("word3", list.get(0).get_word());
        Assertions.assertEquals(6, list.get(0).get_count());
        Assertions.assertEquals("word2", list.get(1).get_word());
        Assertions.assertEquals(5, list.get(1).get_count());
        Assertions.assertEquals("word1", list.get(2).get_word());
        Assertions.assertEquals(4, list.get(2).get_count());

    }


}
