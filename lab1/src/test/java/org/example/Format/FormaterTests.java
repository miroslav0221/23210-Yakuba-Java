package org.example.Format;

import org.example.Report.ReportRow;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FormaterTests {
    @Test
    void format_to_csv_test() {
        List<ReportRow> list = new ArrayList<>();
        list.add(new ReportRow("word1", 5, 12.3));
        list.add(new ReportRow("word2", 10, 18.4));
        list.add(new ReportRow("word3", 12, 22.3));
        Formater formater = new Formater(list);
        formater.format_to_csv();
        try {
            BufferedReader reader = new BufferedReader(new FileReader("filecsv.csv"));
            String line;
            line = reader.readLine();
            Assertions.assertEquals("\uFEFF" + "Слово" + ";" + "Частота" + ";" + "%", line);
            line = reader.readLine();
            Assertions.assertEquals("word1;5;12.300", line);
            line = reader.readLine();;
            Assertions.assertEquals("word2;10;18.400", line);
            line = reader.readLine();
            Assertions.assertEquals("word3;12;22.300", line);


        }
        catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }


    }

}
