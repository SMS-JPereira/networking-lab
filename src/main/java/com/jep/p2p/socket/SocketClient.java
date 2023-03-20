package com.jep.p2p.socket;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;

import static java.lang.System.out;

public class SocketClient {

    public static void main(String[] args) {
        SocketAddress socketAddress = new InetSocketAddress("127.0.0.1", 5005);
        try(SocketChannel socketChannel = SocketChannel.open(socketAddress)) {
            ByteBuffer byteBuffer = ByteBuffer.allocate(64);
            int bytesRead = socketChannel.read(byteBuffer);
            while (bytesRead > 0) {
                byteBuffer.flip();
                while (byteBuffer.hasRemaining()) {
                    out.println((char) byteBuffer.get());
                }
                out.println("\n");
                bytesRead = socketChannel.read(byteBuffer);
            }
        } catch (IOException ioe) {
            ioe.printStackTrace();
        }
    }
}
