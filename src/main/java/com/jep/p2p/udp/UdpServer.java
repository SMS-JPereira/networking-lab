package com.jep.p2p.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;
import java.net.SocketException;

import static java.lang.System.out;

public class UdpServer {

    private static final int PORT = 2082;
    private static DatagramSocket socket;
    private static DatagramPacket inPacket, outPacket;
    private static byte[] buffer;
    public static void main(String[] args) {
        out.println("Opening port . . . \n");
        try {
            socket = new DatagramSocket(PORT);
        } catch (SocketException se) {
            out.println("Unable to open port!");
            out.println(se.getMessage());
            System.exit(1);
        }
        handleClient();
    }

    private static void handleClient() {
        try {
            String messageIn, messageOut;
            int messageCount = 0;
            InetAddress clientAddress = null;
            int clientPort;
            do {
                buffer = new byte[256];
                inPacket = new DatagramPacket(buffer, buffer.length);
                socket.receive(inPacket);

                clientAddress = inPacket.getAddress();
                clientPort = inPacket.getPort();

                messageIn = new String(inPacket.getData(), 0, inPacket.getLength());
                out.println("Message Received!");
                messageCount++;
                messageOut = "Message " + messageCount + " : " + messageIn;

                outPacket = new DatagramPacket(messageOut.getBytes(), messageOut.length(), clientAddress, clientPort);
                socket.send(outPacket);
                out.println("Message : " + messageOut);
            } while (true);
        } catch (IOException ioe) {
            ioe.printStackTrace();
        } finally {
            out.println("\n* Closing the Connection . . . *");
            socket.close();
        }
    }
}
