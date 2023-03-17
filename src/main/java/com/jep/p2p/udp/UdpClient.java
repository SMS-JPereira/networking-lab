package com.jep.p2p.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import static java.lang.System.out;

public class UdpClient {

    private static InetAddress host;
    private static final int PORT = 2082;
    private static DatagramSocket socket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;

    public static void main(String[] args) {
        try {
            host = InetAddress.getLocalHost();
        } catch (UnknownHostException uhe) {
            out.println("Error : " + uhe.getMessage());
            System.exit(1);
        }
        accessServer();
    }

    private static void accessServer() {
        try {
            socket = new DatagramSocket(); // Step 1
            Scanner userEntry = new Scanner(System.in);
            String message = "", response = "";
            do {
                out.println("Enter message : ");
                message = userEntry.nextLine();
                if (!message.equals("***CLOSE***")) {
                    outPacket = new DatagramPacket(message.getBytes(), message.length(), host, PORT);
                    socket.send(outPacket); // Step 3
                    buffer = new byte[256]; // Step 4
                    inPacket = new DatagramPacket(buffer, buffer.length); // Step 5
                    socket.receive(inPacket); // Step 6
                    response = new String(inPacket.getData(), 0, inPacket.getLength()); // Step 7
                    out.println("\n Server Response > " + response);
                }
            } while (!message.equals("***CLOSE***"));
        } catch (IOException ioe) {
            ioe.printStackTrace();
            out.println("Error : " + ioe.getMessage());
        } finally {
            out.println("\n* Closing connection . . . *");
            socket.close(); // Step 8
        }
    }
}
