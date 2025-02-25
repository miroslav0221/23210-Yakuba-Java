package org.example;
import javax.security.auth.login.LoginException;
import java.io.*;
import java.util.Map;
import java.util.Scanner;
import java.util.Stack;

public class StreamParser {
    private final String[] args;
    private BufferedReader reader = null;

    StreamParser(String[] args_) {
        args = args_;
    }

    void create_reader(String name_file) {
        try {
            reader = new BufferedReader(new FileReader(name_file));
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
    }

    int parse_argument() {
        String name_file = "";
        try {
            name_file = args[0];
        }
        catch (Exception e) {
            return 1;
        }
        create_reader(name_file);
        return 0;
    }


    public String next_operation(Map<String, Double> map_var, Stack<Double> stack, String mode) {
        String str = "";
        try {
            if (mode.equals("file")) {
                str = reader.readLine();
            }
            else {
                Scanner in = new Scanner(System.in);
                str = in.nextLine();
            }
            if(str.contains("#") || str.isEmpty()) {
                return "";
            }
            String[] str_split = str.split(" ");
            String operation = str_split[0].toUpperCase();

            switch (operation) {
                case "DEFINE":
                    if(str_split.length != 3) {
                        System.out.println("Error");
                        return "";
                    }
                    try {
                        map_var.put(str_split[1], Double.parseDouble(str_split[2]));
                    }
                    catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    return "";
                case "PUSH":
                    if(str_split.length != 2) {
                        System.out.println("Error");
                        return "";
                    }
                    try {
                        for (Character el : str_split[1].toCharArray()) {
                            if (!Character.isDigit(el)) {
                                Double value = map_var.get(str_split[1]);
                                if (value == null) {
                                    throw new LoginException();
                                }
                                else {
                                    stack.push(value);
                                }
                            }
                        }
                        stack.push(Double.parseDouble(str_split[1]));
                    }
                    catch (Exception e) {
                        System.out.println(e.getLocalizedMessage());
                    }
                    return "";
                case "POP":
                    if(str_split.length != 2 && stack.isEmpty()) {
                        System.out.println("Error");
                        return "";
                    }
                    stack.pop();
                    return "";
                case "PRINT":
                    if(str_split.length != 2 && stack.isEmpty()) {
                        System.out.println("Error");
                        return "";
                    }
                    System.out.println(stack.peek());
                    return "";

                default:
                    return operation;
            }
        }
        catch (Exception e) {
            System.out.println(e.getLocalizedMessage());
        }
        return null;
    }
}
//обработать операцию типа define a 4 push a