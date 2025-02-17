package org.example.StatWords;

import org.example.StatWords.Counter;
import org.example.StatWords.WordStat;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import java.util.HashMap;
import java.util.Map;


public class WordStatTests {
    private WordStat word_stat;

    @BeforeEach
    void set_object() {
        word_stat = new WordStat();
    }

    @Test
    void get_stat_test() {
        Map<String, Counter> map_ = new HashMap<>();
        Assertions.assertEquals(map_, word_stat.get_stat_word_());
    }

    @Test
    void add_firstword_test() {
        word_stat.add_word("word");
        Map<String, Counter> map_ = word_stat.get_stat_word_();
        Assertions.assertEquals(1, map_.get("word").get_count());
    }
    @Test
    void add_word_test() {
        word_stat.add_word("word");
        word_stat.add_word("word");
        Map<String, Counter> map_ = word_stat.get_stat_word_();
        Assertions.assertEquals(2, map_.get("word").get_count());
    }

    @Test
    void get_counts_word_test() {
        word_stat.add_word("word");
        Assertions.assertEquals(1, word_stat.get_counts_words());
        word_stat.add_word("word");
        Assertions.assertEquals(2, word_stat.get_counts_words());
        word_stat.add_word("word");
        Assertions.assertEquals(3, word_stat.get_counts_words());
    }
}