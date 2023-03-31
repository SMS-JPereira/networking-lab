package com.jep.p2p.chat;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Vector;

import static java.lang.System.out;

public class ServerApp {

    Vector<String> users = new Vector<>();
    Vector<HandleClient> clients = new Vector<>();

    public void process() throws Exception {
        ServerSocket server = new ServerSocket(9099, 10);
        out.println("Server Started :");
        while (true) {
            Socket client = server.accept();
            HandleClient hc = new HandleClient(client);
            clients.add(hc);
        }
    }

    public static void main(String[] args) throws Exception {
        new ServerApp().process();
    }

    public void broadcast(String user, String message) {
        // send message to all users
        for (HandleClient c : clients) {
            if (!c.getUserName().equals(user)) {
                c.sendMessage(user, message);
            }
        }
    }

    class HandleClient extends Thread {
        String name = "";
        BufferedReader input;
        PrintWriter output;

        public HandleClient(Socket client) throws IOException {
            input = new BufferedReader(new InputStreamReader(client.getInputStream()));
            output = new PrintWriter(client.getOutputStream(), true);

            name = input.readLine();
            users.add(name);
            start();
        }

        public void sendMessage(String userName, String message) {
            output.println(userName + ": " + message);
        }

        public String getUserName() {
            return name;
        }

        @Override
        public void run() {
            String line;
            try {
                while (true) {
                    line = input.readLine();
                    if (line.equals("end")) {
                        clients.remove(this);
                        users.remove(name);
                        break;
                    }
                    broadcast(name, line);
                }
            } catch (Exception exc) {
                out.println(exc.getMessage());
            }
        }
    }
}
