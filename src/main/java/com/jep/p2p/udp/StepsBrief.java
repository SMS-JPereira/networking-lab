package com.jep.p2p.udp;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.net.InetAddress;

public class StepsBrief {

    public static void main(String[] args) throws IOException {
        // Step 1 : Create a Datagram Socket object
        DatagramSocket socket = new DatagramSocket(2082);

        // Step 2 : Create a buffer for incoming datagrams
        byte[] buffer = new byte[256];

        // Step 3 : Create a Datagram Packet for incoming datagrams
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        // Step 4 : Accept an incoming datagram
        socket.receive(packet);

        // Step 5 : Accept the sender's address and port from the packet
        InetAddress clientAddress = socket.getInetAddress();

        // Step 6 : Retrieve the data from the buffer
        String message = new String(packet.getData(), 0, packet.getLength());

        // Step 7 : Create the response datagram
        DatagramPacket outPacket = new DatagramPacket(message.getBytes(), message.length(), clientAddress, packet.getPort());

        // Step 8 : Send the response datagram
        socket.send(outPacket);

        // Step 9 : Close the Datagram Socket !
        socket.close();
    }
}
