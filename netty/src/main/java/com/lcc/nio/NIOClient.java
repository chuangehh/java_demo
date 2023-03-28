package com.lcc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SocketChannel;
import java.nio.charset.StandardCharsets;

public class NIOClient {

    public static void main(String[] args) throws IOException {
        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);

        if (!socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999))) {
            while (!socketChannel.finishConnect()) {
                System.out.println(Thread.currentThread().getName() + "还未链接上，继续搞事情");
            }
        }

        socketChannel.write(ByteBuffer.wrap("我去，牛逼".getBytes(StandardCharsets.UTF_8)));
        System.in.read();
    }

}
