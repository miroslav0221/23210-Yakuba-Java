package org.example.Report;
import org.example.Report.ReportRow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

public class ReportRowTests {
    private ReportRow report_row;

    @BeforeEach
    void set_object() {
        report_row = new ReportRow();
    }

    @Test
    void get_word_test() {
        report_row.set_word("word");
        Assertions.assertEquals("word", report_row.get_word());
    }
    @Test
    void get_freq_test() {
        report_row.set_count(13);
        Assertions.assertEquals(13, report_row.get_count());
    }
    @Test
    void get_count_test() {
        report_row.set_count(20);
        report_row.set_frequency(100);
        Assertions.assertEquals(20.0, report_row.get_frequency());
    }
}
