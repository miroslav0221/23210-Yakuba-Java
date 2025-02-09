package org.example;
import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.io.*;

public class Parser
{

    private String name_file_;
    private Map<String, Integer> map_word_;
    private List<Map.Entry<String, Integer>> list_;
    private Integer count_words_ = 0;

    public static void main(String[] args)
    {

        Parser parser = new Parser();
        parser.init_name_file(args);
        parser.read_file();
        parser.process_map();
        parser.write_file();
        System.out.println("Complete! =)");


    }
    void process_map()
    {
        list_ = new ArrayList<>(map_word_.entrySet());
        Comparator<Map.Entry<String, Integer>> comparator = Comparator.comparing(Map.Entry::getValue);
        list_.sort(comparator);
        Collections.reverse(list_);
    }

    public void write_file()
    {
        Writer writer = null;
        try
        {
            writer = new OutputStreamWriter(new FileOutputStream("filecsv.csv"), "UTF-8");
            writer.write("\uFEFF");
            writer.write("Слово" + ";" + "Частота" + ";" + "%" + "\n");
            for (Map.Entry<String, Integer> pair : list_.stream().toList()) {
                double val_frequency = 100.0 * pair.getValue()/count_words_;
                BigDecimal val_freq = new BigDecimal(val_frequency);
                val_freq = val_freq.setScale(3, RoundingMode.HALF_UP);
                writer.write(pair.getKey() + ";" + pair.getValue() + ";"
                        + val_freq + "\n");
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

    void init_name_file(String[] args)
    {
        try
        {
            name_file_ = args[0];
        }
        catch (Exception e)
        {

            System.err.println("No file\n" + e.getLocalizedMessage());
        }
    }

    public void read_file()
    {
        map_word_ = new HashMap<>();
        Reader reader = null;
        try
        {
            reader = new InputStreamReader(new FileInputStream(name_file_), "UTF-8");
            StringBuilder word = new StringBuilder();
            int symbol = reader.read();
            while(symbol != -1) {
                if (Character.isLetter(symbol) || Character.isDigit(symbol)) {
                    word.append((char) symbol);
                }
                else {
                    if (word.isEmpty()) {
                        symbol = reader.read();
                        continue;
                    }
                    if (!map_word_.containsKey(word.toString())) {
                        map_word_.put(word.toString(), 1);
                    }
                    else {
                        map_word_.put(word.toString(), map_word_.get(word.toString()) + 1);
                    }
                    count_words_++;
                    word.setLength(0);
                }
                symbol = reader.read();
            }
        }
        catch (IOException e)
        {
            System.err.println(e.getLocalizedMessage());
        }
        finally
        {
            if (null != reader)
            {
                try
                {
                    reader.close();
                }
                catch (IOException e)
                {
                    e.printStackTrace(System.err);
                }
            }
        }
    }
}