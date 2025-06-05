package org.example.Client;

import org.example.Message.Message;
import org.example.View.ClientWindow;
import org.w3c.dom.Document;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
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
    private DataOutputStream out;
    private DataInputStream in;
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
            StringBuilder disconnect = new StringBuilder();
            disconnect.append("<command name=\"message\">")
                    .append("<username>").append("System").append("</username>")
                    .append("<text>").append(username).append(" disconnected").append("</text>")
                    .append("<time>").append(calcTimeCurrent()).append("</time>")
                    .append("</command>");
            try {
                byte[] data = disconnect.toString().getBytes("UTF-8");
                out.writeInt(data.length);
                out.write(data);
                out.flush();
            }
            catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
            }


            StringBuilder sb = new StringBuilder();
            sb.append("<command name=\"disconnect\">")
                    .append("</command>");
            try {
                byte[] data = sb.toString().getBytes("UTF-8");
                out.writeInt(data.length);
                out.write(data);
                out.flush();
            }
            catch (Exception e) {
                System.err.println(e.getLocalizedMessage());
            }
            in.close();
            out.close();
            socketClient.close();
        }
        catch (Exception e) {
            System.err.println("Failed finish thread");
        }
    }






    public void sendXML(String text) {
        StringBuilder sb = new StringBuilder();
        sb.append("<command name=\"message\">")
                .append("<username>").append(username).append("</username>")
                .append("<text>").append(text).append("</text>")
                .append("<time>").append(calcTimeCurrent()).append("</time>")
                .append("</command>");
        try {
            byte[] data = sb.toString().getBytes("UTF-8");
            out.writeInt(data.length);
            out.write(data);
            out.flush();
        }
        catch (Exception e) {
            System.err.println("Failed write");
        }

    }

    public void sendUsername() throws IOException {
        StringBuilder sb = new StringBuilder();
        sb.append("<command name=\"login\">")
                .append("<username>").append(username).append("</username>")
                .append("</command>");
        byte[] data = sb.toString().getBytes("UTF-8");
        out.writeInt(data.length);
        out.write(data);
        out.flush();
    }

    @Override
    public void run() {
        isWorking = true;
        System.out.println("Я запущен");
        try {
            socketClient = new Socket("localhost", port);
            System.out.println("Я установил соединение");
            //BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
            out = new DataOutputStream(socketClient.getOutputStream());
            out.flush();
            in = new DataInputStream(socketClient.getInputStream());

            //getListMessages();

            window.setCurrentUsername(username);
            sendUsername();

            readerThread = new ReaderThread(in, window);
            readerThread.start();
            Message messageConnected = new Message(" ", username + " connected", calcTimeCurrent());
            sendXML(messageConnected.getText());
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
}
