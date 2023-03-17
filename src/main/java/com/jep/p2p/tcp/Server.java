package com.jep.p2p.tcp;

import java.io.*;
import java.net.*;
import java.util.Scanner;

import static java.lang.System.out;

public class Server {

    private static ServerSocket serverSocket;
    private static final int PORT = 1234;

    public static void main(String[] args) throws UnknownHostException {
        out.println("Opening Port: " + PORT + "\n");
        try {
            serverSocket = new ServerSocket(PORT); //Step 1
        } catch (IOException e) {
            out.printf("Unable to attach to port %d ! Exception: %s", PORT, e.getMessage());
            System.exit(1);
        }
        do {
            handleClient();
        } while (true);
    }

    private static void handleClient() {
        Socket socket = null;
        try {
            socket = serverSocket.accept(); //Step 2

            Scanner input = new Scanner(socket.getInputStream());
            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true); //Step 3

            int messagesCount = 0;
            String message = input.nextLine(); //Step 4
            while (!message.equals("***CLOSES***")) {
                out.println("Message is received!");
                messagesCount++;
                writer.println(String.format("Message number %d : %s", messagesCount, message)); //Step 4
                message = input.nextLine();
            }
            writer.println(messagesCount + " messages received."); // Step 4
        } catch (IOException exc) {
            exc.printStackTrace();
            out.println(exc.getMessage());
        } finally {
            try {
                out.println("\n* Closing connection . . . *");
                assert socket != null;
                socket.close(); // Step 5
            } catch (IOException ioe) {
                out.println("Unable to disconnect! Exception: " + ioe.getMessage());
                System.exit(1);
            }
        }
    }
}