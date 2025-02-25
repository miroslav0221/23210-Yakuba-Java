package org.example.Format;

import org.example.Report.ReportRow;

import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.List;

public class Formater {
    List<ReportRow> list_;
    public Formater(List<ReportRow> list) {
        list_ = list;
    }
    public void format_to_csv() {
        Writer writer = null;
        try
        {
            writer = new OutputStreamWriter(new FileOutputStream("filecsv.csv"), "UTF-8");
            writer.write("\uFEFF");
            writer.write("Слово" + ";" + "Частота" + ";" + "%" + "\n");
            for(ReportRow element : list_) {
                BigDecimal val_freq = new BigDecimal(element.get_frequency());
                val_freq = val_freq.setScale(3, RoundingMode.HALF_DOWN);
                writer.write(element.get_word() + ";" + element.get_count() +
                        ";" + val_freq + "\n");
            }
        }
        catch (IOException e)
        {
            System.err.println(e.getLocalizedMessage());
        }
        finally
        {
            if (null != writer) {
                try {
                    writer.close();
                }
                catch (IOException e) {
                    System.err.println(e.getLocalizedMessage());

                }
            }
        }
    }
}
