package org.example.Parser;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.Calculator.Context;
import org.example.Main;

import javax.security.auth.login.LoginException;
import java.io.*;
import java.nio.file.Paths;
import java.util.Scanner;


public class StreamParser {
    static final Logger parserLogger = LogManager.getLogger(StreamParser.class.getName());
    private final String[] args;
    private BufferedReader reader = null;
    private final Context context;
    private String mode = "console";
    private Scanner in;

    public StreamParser(String[] args_, Context context_) {
        args = args_;
        context = context_;
    }
    
    void create_reader(String name_file) throws Exception {
        try {
            reader = new BufferedReader(new FileReader(name_file));
            parserLogger.debug("Файл считан");
        }
        catch (Exception e) {
            parserLogger.error("Не удалось считать файл");
            System.out.println(e.getLocalizedMessage());
            throw new Exception();
        }
    }

    public int parse_argument() {
        String name_file = "";
        try {
            name_file = args[0];
            parserLogger.debug("Получен путь к файлу");
            create_reader(name_file);
        }
        catch (Exception e) {
            parserLogger.error("Поданы не корректные аргументы");
            return 1;
        }
        return 0;
    }

    public void set_mode(String mode_) {
        mode = mode_;
        if (mode.equals("console")) {
            in = new Scanner(System.in);
        }
    }

    String get_string() throws IOException {
        String str = "";
        if (mode.equals("file")) {
            str = reader.readLine();
        }
        else {
            str = in.nextLine();
        }
        return str;
    }

    public String next_operation() {
        try {
            String str = get_string();
            parserLogger.debug("Считана строка");
            if(str.contains("#") || str.isEmpty()) {
                parserLogger.debug("Получена строка с комментарием");
                return "";
            }
            String[] str_split = str.split(" ");
            String operation = str_split[0].toUpperCase();

            switch (operation) {
                case "DEFINE":
                    if(str_split.length != 3) {
                        parserLogger.error("Неверный формат ввода команды define");
                        System.out.println("Error");
                        return "";
                    }
                    try {
                        context.get_map().put(str_split[1], Double.parseDouble(str_split[2]));
                        parserLogger.debug("Создан макрос");

                    }
                    catch (Exception e) {
                        parserLogger.error("Не удалось создать макрос");
                        System.out.println(e.getLocalizedMessage());
                    }
                    return "";
                case "PUSH":
                    if(str_split.length != 2) {
                        parserLogger.error("Неверный формат ввода команды push");
                        System.out.println("Error");
                        return "";
                    }
                    try {
                        for (Character el : str_split[1].toCharArray()) {
                            if (!Character.isDigit(el) && !(el == '.') && !(el=='-')) {
                                Double value = context.get_map().get(str_split[1]);
                                if (value == null) {
                                    throw new Exception();
                                }
                                else {
                                    parserLogger.debug("Запушено значение");
                                    context.get_stack().push(value);
                                    return "";
                                }
                            }
                        }
                        context.get_stack().push(Double.parseDouble(str_split[1]));
                        parserLogger.debug("Запушено значение");
                    }
                    catch (Exception e) {
                        parserLogger.error("Не верное значение аргументов(нет такого макроса)");
                        System.out.println(e.getLocalizedMessage());
                    }
                    return "";
                case "POP":
                    if(context.get_stack().isEmpty()) {
                        parserLogger.debug("Нет элементов в стеке pop");
                        System.out.println("Нет элементов в стеке");
                        return "";
                    }
                    context.get_stack().pop();
                    parserLogger.debug("pop выполнен");
                    return "";
                case "PRINT":
                    if(context.get_stack().isEmpty()) {
                        parserLogger.debug("Нет элементов в стеке print");
                        System.out.println("Нет элементов в стеке");
                        return "";
                    }

                    System.out.println(context.get_stack().peek());
                    parserLogger.debug("print выполнен");
                    return "";

                default:
                    return operation;
            }
        }
        catch (Exception e) {
            parserLogger.error("Файл закончен или произошла ошибка в чтении");
            System.err.println("Файл закончен или произошла ошибка в чтении");
        }
        return null;
    }
}
