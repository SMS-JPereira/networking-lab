package com.jep.p2p.tcp;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.InetAddress;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.Scanner;

import static java.lang.System.out;

public class ClientSide {

    private static InetAddress host;
    private static final int PORT = 1234;

    public static void main(String[] args) {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException uhe) {
            out.println("Error caused: " + uhe.getMessage());
            System.exit(1);
        }
        accessServer();
    }

    private static void accessServer() {
        Socket socket = new Socket();
        try {
            socket = new Socket(host, PORT); // Step 1
            Scanner input = new Scanner(socket.getInputStream()); // Step 2

            PrintWriter writer = new PrintWriter(socket.getOutputStream(), true);
            Scanner userEntry = new Scanner(System.in);
            String message, response;
            do {
                out.println("Enter message : ");
                message = userEntry.nextLine();
                writer.println(message); // Step 3
                response = input.nextLine();
                out.println("\nServer > " + response);
            } while (!message.equals("***CLOSES***"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            try {
                out.println("\n* Closing Connection . . . *");
                socket.close(); // Step 4
            } catch (IOException exc) {
                out.println("Unable to Disconnect! Error: " + exc.getMessage());
                System.exit(1);
            }
        }
    }
}
