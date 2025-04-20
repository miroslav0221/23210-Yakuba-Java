package org.example;
import org.example.CarParts.*;
import org.example.Factory.Factory;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class Main {
    public static void main(String[] args) throws IOException {
        Factory factory = new Factory("config.properties");


//        String fileName = "config.properties";
//        Properties properties = new Properties();
//        File file = new File(fileName);
//        properties.load(new FileReader(file));
//        System.out.println(Integer.parseInt(properties.getProperty("BodyStorageSize")));
    }
}