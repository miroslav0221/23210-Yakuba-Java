package org.example;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import java.io.ByteArrayOutputStream;

import java.io.PrintStream;

public class MainTest {
    @Test
    void test_main() {
        String[] args = {"TestMain"};
        PrintStream originalOut = System.out;
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outputStream));
        Main.main(args);
        System.setOut(originalOut);
        Assertions.assertEquals("200.0", outputStream.toString().trim());
    }
}
