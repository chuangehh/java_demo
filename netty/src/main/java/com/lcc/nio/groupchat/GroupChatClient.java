package com.lcc.nio.groupchat;


import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;
import java.util.Set;

public class GroupChatClient {

    public static void main(String[] args) throws IOException, InterruptedException {
        Selector selector = Selector.open();

        SocketChannel socketChannel = SocketChannel.open();
        socketChannel.configureBlocking(false);
        socketChannel.register(selector, SelectionKey.OP_READ);

        boolean connect = socketChannel.connect(new InetSocketAddress("127.0.0.1", 9999));
        if (!connect) {
            while (!socketChannel.finishConnect()) {
                System.out.println(Thread.currentThread().getName() + "还未链接上，继续搞事情");
            }
        }
        System.out.println(socketChannel.getLocalAddress() + "启动..");


        // 3秒读取一次服务端的消息
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (true) {
                    try {
                        int select = selector.select(1000);
                        Set<SelectionKey> selectionKeys = selector.selectedKeys();

                        Iterator<SelectionKey> iterator = selectionKeys.iterator();
                        while (iterator.hasNext()) {
                            SelectionKey selectionKey = iterator.next();
                            if (selectionKey.isReadable()) {
                                SocketChannel channel = (SocketChannel) selectionKey.channel();
                                channel.configureBlocking(false);


                                ByteBuffer buffer = ByteBuffer.allocate(1024);
                                int read = channel.read(buffer);
                                String message = new String(buffer.array(), 0, read);
                                System.out.println(message);
                            }
                            iterator.remove();
                        }
                        Thread.sleep(3000);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }

            }
        }).start();

        Scanner scanner = new Scanner(System.in);
        while (scanner.hasNext()) {
            String str = scanner.nextLine();
            socketChannel.write(ByteBuffer.wrap(str.getBytes()));
        }

    }

}
