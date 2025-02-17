package org.example.Report;

public class ReportRow {
    private String word;
    private int count;
    private double frequency = 0.0;
    ReportRow() {}

    public ReportRow(String word_, Integer count_, Double frequency_) {
        word = word_;
        count = count_;
        frequency = frequency_;
    }

    void set_word(String word_) {
        word = word_;
    }
    void set_frequency(Integer count_words) {
        frequency = 100.0 * count/count_words;
    }
    void set_count(int new_count) {
        count = new_count;
    }
    public double get_frequency() {
        return frequency;
    }
    public int get_count() {
        return count;
    }
    public String get_word() {
        return word;
    }
}
