package org.example.Client;

import org.example.Message.Message;
import org.example.View.ClientWindow;

import java.io.*;
import java.net.Socket;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Objects;

public class ClientThread extends Thread {
    private Socket socketClient;
    private final int port = 8080;
    private ObjectOutputStream out;
    private ObjectInputStream in;
    private final String username;
    private ClientWindow window;
    private boolean isWorking;
    private ReaderThread readerThread;

    public ClientThread(String username, ClientWindow window) {
        this.username = username;
        this.window = window;
    }

    public void stopWorking() {
        isWorking = false;
        readerThread.stopWorking();
        try {
            out.writeObject(new Message("", username+" disconnected", calcTimeCurrent()));
            Message message = new Message("System", "Disconnect", calcTimeCurrent());
            out.writeObject(message);
            out.writeObject(username);
            socketClient.close();
            in.close();
            out.close();
        }
        catch (Exception e) {
            System.err.println("Failed finish thread");
        }
    }

    private void getListMessages() {
        try {
            ArrayList<Message> messages = (ArrayList<Message>)in.readObject();
            for (Message message : messages) {
                if(message == null || message.getText().isEmpty()) {
                    continue;
                }
                if(message.getText().charAt(0) == '/') {
                    continue;
                }
                window.addMessage(message);
                System.out.println(message.getText());
            }
        }
        catch (Exception e) {
            System.err.println("Failed get array messages");
        }
    }


    public void sendMessage(Message message) throws IOException {
        out.writeObject(message);
    }


    @Override
    public void run() {
        isWorking = true;
        System.out.println("Я запущен");
        try {
            socketClient = new Socket("localhost", port);
            System.out.println("Я установил соединение");
            BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            out = new ObjectOutputStream(socketClient.getOutputStream());
            out.flush();
            in = new ObjectInputStream(socketClient.getInputStream());

            getListMessages();

            window.setCurrentUsername(username);
            out.writeObject(username);

            readerThread = new ReaderThread(in, window);
            readerThread.start();
            Message messageConnected = new Message("", username + " connected", calcTimeCurrent());
            out.writeObject(messageConnected);
            while (isWorking) {
                String text = reader.readLine();
                ReaderThread.usersMessage = Objects.equals(text, "/users");
                Message message = new Message(username, text, calcTimeCurrent());
                //sendMessage(message);
                System.out.println("Отправили сообщение");
                Thread.sleep(100);
//                try {
//                    Message messageAnswer = (Message)in.readObject();
//                    System.out.println("На клиенте получили" + messageAnswer.getText());
//                }
//                catch (Exception e) {
//                    System.err.println("Error message answer server");
//                }

            }
        }
        catch (Exception e) {
            throw new RuntimeException(e + "Client socket create failed");
        }
    }

    private String calcTimeCurrent() {
        ZonedDateTime timeInNovosibirsk = ZonedDateTime.now(ZoneId.of("Asia/Novosibirsk"));

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM HH:mm");
        return timeInNovosibirsk.format(formatter);
    }

    public void sendMessageFromGUI(String text) {
        try {
            String time = calcTimeCurrent();
            if (out != null && text != null && !text.isEmpty()) {
                Message message = new Message(username, text, time); // TODO: тут можно время настоящее вставить
                out.writeObject(message);
                out.flush();
                //window.addMessage(message); // 👈 Передаём юзернейм, текст и время
            }
        } catch (Exception e) {
            System.err.println("Failed to send message");
        }
    }


}
