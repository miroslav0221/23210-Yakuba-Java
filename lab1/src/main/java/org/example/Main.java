package org.example;
import org.example.Parser.StreamParser;

public class Main {

    public static void main(String[] args) {
        StreamParser stream_parser = new StreamParser();
        stream_parser.parse_argument(args);
        Controller controller = new Controller(stream_parser);
        controller.convert();

    }
}