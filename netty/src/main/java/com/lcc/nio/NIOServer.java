package com.lcc.nio;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class NIOServer {

    public static void main(String[] args) throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();

        Selector selector = Selector.open();
        serverSocketChannel.socket().bind(new InetSocketAddress(9999));
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            if (selector.select(1000) == 0) {
                System.out.println("1秒内没有链接");
            }

            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            if (selectionKeys.size() > 0) {
                System.out.println("selectionKeys.size() " + selectionKeys.size());
                System.out.println("selector.selectedKeys()" + selector.selectedKeys());
                System.out.println("selector.keys()" + selector.keys());
            }

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectedKey = iterator.next();
                if (selectedKey.isAcceptable()) {
                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println("服务端链接：" + socketChannel.getRemoteAddress());
                }

                if (selectedKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectedKey.channel();

                    ByteBuffer buffer = ByteBuffer.allocate(1024);
                    channel.read(buffer);
                    System.out.println("服务端读取数据：" + new String(buffer.array()));
                }

                iterator.remove();
            }


        }
    }

}
