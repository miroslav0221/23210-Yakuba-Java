package org.example.StatWords;

import java.util.HashMap;
import java.util.Map;

public class WordStat {

    private Map<String, Counter> stat_word_;
    private int count_words;

    public WordStat() {
        stat_word_ = new HashMap<>();
        count_words = 0;
    }

    public void add_word(String word) {

        if (!stat_word_.containsKey(word)) {
            Counter counter = new Counter();
            stat_word_.put(word, counter);
        }
        else {
            stat_word_.get(word).add();
        }
        count_words++;
    }

    public Map<String, Counter> get_stat_word_() {
        return stat_word_;
    }

    public int get_counts_words() {
        return count_words;
    }
}
