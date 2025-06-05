package org.example.Server;

import org.example.Message.Message;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.xml.sax.InputSource;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.*;
import java.net.Socket;
import java.util.Objects;

public class UserThread extends Thread {
    private Socket socket;
    private DataInputStream in;
    private DataOutputStream out;
    private boolean isWorking;
    private String username;

    public UserThread(Socket socket) throws Exception {
        this.socket = socket;
        out = new DataOutputStream(socket.getOutputStream());
        out.flush();
        in = new DataInputStream(socket.getInputStream());
    }

    private void sendListMessages() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("<command name=\"messagelist\">");
            synchronized (Server.messages) {
                for (Message message : Server.messages) {
                    sb.append("<text>").append(message.getText()).append("</text>");
                    sb.append("<username>").append(message.getUsername()).append("</username>");
                    sb.append("<time>").append(message.getSendingTime()).append("</time>");
                }
                sb.append("</command>");
                sendXml(sb.toString());
            }
        } catch (Exception e) {
            System.err.println(e.getLocalizedMessage());
        }
    }

    private void sendXml(String xml) throws IOException {
        byte[] data = xml.getBytes("UTF-8");
        out.writeInt(data.length);
        out.write(data);
        out.flush();
    }

    private void sendMessageXml(Message m) throws IOException {
        System.out.println("я отправил сообщение");
        StringBuilder sb = new StringBuilder();
        sb.append("<command name=\"message\">")
                .append("<username>").append(m.getUsername()).append("</username>")
                .append("<text>").append(m.getText()).append("</text>")
                .append("<time>").append(m.getSendingTime()).append("</time>")
                .append("</command>");
        sendXml(sb.toString());

        synchronized (Server.loger) {
            Server.loger.sendMessage(m);
        }
    }






//    private void stopWorking() {
//        isWorking = false;
//        try {
//            if (socket != null) socket.close();
//            if (in != null) in.close();
//            if (out != null) out.close();
//        }
//        catch (IOException e) {
//            System.err.println("Failed to close resources");
//        }
//
//        synchronized (Server.loger) {
//            Server.loger.deleteUser(username);
//        }
//
//        synchronized (Server.usersList) {
//            Server.usersList.remove(username);
//        }
//        synchronized (Server.userThreads) {
//            Server.userThreads.remove(this);
//        }
//
//        broadcastUserList();
//    }

    private void broadcastMessage(Message message) {
        synchronized (Server.userThreads) {
            for (UserThread thread : Server.userThreads) {
                try {
                    if (thread != null) {
                        thread.sendMessageXml(message);
                    }
                }
                catch (Exception e) {
                    System.err.println("Failed to send message to client");
                }
            }
        }
    }

    private void processLogin() throws Exception {
        String xml = readXml();
        Document doc = parseXml(xml);
        Element cmd = doc.getDocumentElement();
        if (!"login".equals(cmd.getAttribute("name"))) {
            throw new IllegalStateException("Expected <command name=\"login\">");
        }

        username = cmd.getElementsByTagName("username").item(0).getTextContent();

        synchronized (Server.loger) {
            Server.loger.connectUser(username);
        }
        synchronized (Server.usersList) {
            Server.usersList.add(username);
        }




        System.out.println("User logged in: " + username);
    }

    private void sendUserList() {
        try {
            StringBuilder sb = new StringBuilder();
            sb.append("<command name=\"userlist\">");
            synchronized (Server.usersList) {
                for (String user : Server.usersList) {
                    sb.append("<user>").append(user).append("</user>");
                }
            }
            sb.append("</command>");
            sendXml(sb.toString());

            synchronized (Server.loger) {
                Server.loger.sendListUsers();
            }
        } catch (IOException e) {
            System.err.println("Failed to send user list to " + username);
        }
    }

    @Override
    public void run() {
        isWorking = true;
        try {
            processLogin();
            sendListMessages();
            synchronized (Server.userThreads) {
                for (UserThread t : Server.userThreads) {
                    t.sendUserList();
                }
            }

            while (isWorking) {
                String xml = readXml();
                System.out.println("Я получил сообщение");
                Document doc = parseXml(xml);
                processCommand(doc);
                System.out.println("size: " + Server.messages.size());
            }
        }
        catch (Exception e) {
            System.err.println("Error in client thread (" + username + "): " + e.getMessage());
        }
        finally {
            stopWorking();
        }
    }

    private void stopWorking() {
        try {
            socket.close();
        }
        catch (IOException ignored) {
        }

        synchronized (Server.loger) {
            Server.loger.deleteUser(username);
        }
        synchronized (Server.usersList) {
            Server.usersList.remove(username);
        }
        synchronized (Server.userThreads) {
            for (UserThread t : Server.userThreads) {
                t.sendUserList();
            }
            Server.userThreads.remove(this);
        }

        System.out.println("User disconnected: " + username);
    }


    private void processCommand(Document doc) throws Exception {
        Element cmd = doc.getDocumentElement();
        String name = cmd.getAttribute("name");

        switch (name) {
            case "message":
                String user = cmd.getElementsByTagName("username").item(0).getTextContent();
                String text = cmd.getElementsByTagName("text").item(0).getTextContent();
                String time = cmd.getElementsByTagName("time").item(0).getTextContent();
                Message message = new Message(user, text, time);
                synchronized (Server.loger) {
                    Server.loger.wasGetMessage(message);
                }
                synchronized (Server.messages) {
                    Server.messages.add(message);
                }
                broadcastMessage(message);
                break;

            case "disconnect":
                isWorking = false;
                break;

            default:
                System.err.println("Unknown command from " + username + ": " + name);
        }
    }

    private Document parseXml(String xml) throws Exception {
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        DocumentBuilder builder = factory.newDocumentBuilder();
        return builder.parse(new InputSource(new StringReader(xml)));
    }

    private String readXml() throws IOException {
        int length = in.readInt();
        byte[] buf = new byte[length];
        in.readFully(buf);
        return new String(buf, "UTF-8");
    }

}