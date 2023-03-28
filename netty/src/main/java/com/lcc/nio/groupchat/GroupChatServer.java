package com.lcc.nio.groupchat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

public class GroupChatServer {

    public static void main(String[] args) throws IOException, InterruptedException {
        Selector selector = Selector.open();

        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open();
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.bind(new InetSocketAddress(9999));
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);
        System.out.println(serverSocketChannel.getLocalAddress() + "启动..");

        // 群聊系统

        // 1、服务端启动
        while (true) {
            System.out.println("select 线程：" + Thread.currentThread().getName() );
            selector.select();

            Set<SelectionKey> allKeys = selector.keys();
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            while (selectionKeys.size() == 0) {
                System.out.println("1秒没有客户端链接：" + allKeys);
                Thread.sleep(1000);
            }

            Iterator<SelectionKey> iterator = selectionKeys.iterator();
            while (iterator.hasNext()) {
                SelectionKey selectionKey = iterator.next();
                if (selectionKey.isAcceptable()) {
                    System.out.println("accept 线程：" + Thread.currentThread().getName() );

                    SocketChannel socketChannel = serverSocketChannel.accept();
                    socketChannel.configureBlocking(false);
                    socketChannel.register(selector, SelectionKey.OP_READ);
                    System.out.println(socketChannel.getRemoteAddress() + "：上线");
                }
                if (selectionKey.isReadable()) {
                    System.out.println("read 线程：" + Thread.currentThread().getName() );

                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    try {
                        ByteBuffer buffer = ByteBuffer.allocate(1024);
                        int read = channel.read(buffer);

                        // 群发消息
                        String client = channel.getRemoteAddress().toString();
                        String message = client + ": " + new String(buffer.array(), 0, read);
                        System.out.println(message);

                        for (SelectionKey otherClient : allKeys) {
                            if (otherClient.channel() instanceof SocketChannel && otherClient.channel() != channel ) {
                                SocketChannel otherChannel = (SocketChannel) otherClient.channel();
                                otherChannel.write(ByteBuffer.wrap(message.getBytes()));
                            }
                        }
                    } catch (IOException e) {
                        try {
                            System.out.println(channel.getRemoteAddress() + ": 离线了");
                            channel.close();
                            selectionKey.cancel();
                        } catch (IOException ex) {
                            ex.printStackTrace();
                        }
                    }

                }

                iterator.remove();
            }

        }


    }

}
