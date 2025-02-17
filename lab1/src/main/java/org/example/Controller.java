package org.example;
import org.example.Format.Formater;
import org.example.Parser.StreamParser;
import org.example.Report.ReportGenerator;
import org.example.StatWords.WordStat;

public class Controller {
    final private StreamParser stream_parser_;
    final private WordStat stat;
    Controller(StreamParser stream_parser) {
        stream_parser_ = stream_parser;
        stat = new WordStat();

    }
    public void convert() {
        create_stat();
        ReportGenerator report_words = new ReportGenerator();
        report_words.map_to_list(stat.get_stat_word_(), stat.get_counts_words());
        Formater formater = new Formater(report_words.get_list());
        formater.format_to_csv();

    }
    void create_stat() {
        StringBuilder word = stream_parser_.next_word();
        while (word != null) {
            if(!word.isEmpty()) {
                stat.add_word(word.toString());
            }
            word = stream_parser_.next_word();
        }
    }

}
