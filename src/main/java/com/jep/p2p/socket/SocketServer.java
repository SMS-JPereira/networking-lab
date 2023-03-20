package com.jep.p2p.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Date;

import static java.lang.System.out;

public class SocketServer {

    public static void main(String[] args) {
        out.println("Time server is Started");
        try {
            ServerSocketChannel serverSocketChannel = ServerSocketChannel.open(); // create new socket
            serverSocketChannel.socket().bind(new InetSocketAddress(5005)); //bind socket to port 5005
            while (true) {
                out.println("Waiting for request . . .");
                SocketChannel socketChannel = serverSocketChannel.accept();
                if (socketChannel != null) {
                    String dateTimeMsg = "Date : " + new Date(System.currentTimeMillis());
                    ByteBuffer buffer = ByteBuffer.allocate(64);
                    buffer.put(dateTimeMsg.getBytes());
                    buffer.flip();
                    while (buffer.hasRemaining()) {
                        socketChannel.write(buffer);
                    }
                    out.println("Sent : " + dateTimeMsg);
                }
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
