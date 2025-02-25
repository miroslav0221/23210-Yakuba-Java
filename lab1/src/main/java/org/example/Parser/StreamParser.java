package org.example.Parser;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.Reader;

public class StreamParser {
    private Reader reader = null;

    void create_reader(String name_file) {
        try
        {
            reader = new InputStreamReader(new FileInputStream(name_file), "UTF-8");
        }
        catch (IOException e)
        {
            System.err.println(e.getLocalizedMessage());
            throw new IllegalArgumentException();
        }
    }

    public void parse_argument(String[] args) {
        String name_file = "";
        try
        {
            name_file = args[0];
        }
        catch (Exception e)
        {
            System.err.println("No file\n" + e.getLocalizedMessage());
            throw new IllegalArgumentException();
        }
        create_reader(name_file);
    }

    public StringBuilder next_word() {
        StringBuilder word = new StringBuilder();
        try {
            int symbol = reader.read();
            if (symbol == -1) {
                reader.close();
                return null;
            }
            while(true) {
                if(Character.isLetterOrDigit(symbol)) {
                    word.append((char) symbol);
                    symbol = reader.read();
                }
                else {
                    break;
                }
            }

        }
        catch (IOException e) {
            System.out.println(e.getLocalizedMessage() + "дадада");
            throw new RuntimeException();
        }
        return word;
    }

}
