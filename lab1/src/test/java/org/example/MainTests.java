package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class MainTests {
    @Test
    void main_test() {
        String[] args = {"test.txt"};
        Main.main(args);
        try {
            BufferedReader reader = new BufferedReader(new FileReader("filecsv.csv"));
            String line;
            line = reader.readLine();
            Assertions.assertEquals("\uFEFF" + "Слово" + ";" + "Частота" + ";" + "%", line);
            line = reader.readLine();
            Assertions.assertEquals("word4;1;25.000", line);
            line = reader.readLine();
            Assertions.assertEquals("word2;1;25.000", line);
            line = reader.readLine();
            Assertions.assertEquals("word3;1;25.000", line);
            line = reader.readLine();
            Assertions.assertEquals("word1;1;25.000", line);
        }
        catch (IOException e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

}
