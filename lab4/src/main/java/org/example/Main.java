package org.example;
import org.example.CarParts.*;
import org.example.Controller.Controller;
import org.example.Factory.Factory;
import org.example.View.View;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;


public class Main {
    public static void main(String[] args) throws IOException {
        Factory factory = new Factory("config.properties");
        Controller controller = new Controller(factory);
        View view = new View(factory, controller);

    }
}