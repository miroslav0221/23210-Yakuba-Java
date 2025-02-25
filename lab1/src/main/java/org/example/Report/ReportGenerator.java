package org.example.Report;

import org.example.StatWords.Counter;

import java.util.*;
public class ReportGenerator {
    private final List<ReportRow> list_;

    public ReportGenerator() {
        list_ = new ArrayList<>();
    }

    public void map_to_list(Map<String, Counter> map_, Integer count_words) {
        for(Map.Entry<String, Counter> pair : map_.entrySet()) {
            ReportRow element = new ReportRow();
            element.set_word(pair.getKey());
            element.set_count(pair.getValue().get_count());
            element.set_frequency(count_words);
            list_.add(element);
        }
        sort_list();
    }

    void sort_list() {
        Comparator<ReportRow> comparator = Comparator.comparing(ReportRow::get_count);
        list_.sort(comparator);
        Collections.reverse(list_);
    }

    public List<ReportRow> get_list() {
        return list_;
    }
}
