package com.lcc.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Scanner;

/**
 * 群聊系统: 客户端
 * <p>
 * 功能:
 * 1.main 线程负责从键盘读取数据
 * 2.新建一个线程负责Selector 接收群聊工作
 *
 * @author liangchuanchuan
 */
public class GroupChatClient {


    private SocketChannel channel;
    private Selector selector;
    private String host = "127.0.0.1";
    private int port = 1010;

    public GroupChatClient() {
        try {
            selector = Selector.open();
            // 此处有大坑
            // channel = SocketChannel.open(new InetSocketAddress(port));
            channel = SocketChannel.open(new InetSocketAddress(host, port));
            channel.configureBlocking(false);
            channel.register(selector, SelectionKey.OP_READ);
            System.out.println("客户端链接服务器");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void sendMessage(String message) {
        try {
            channel.write(ByteBuffer.wrap(message.getBytes("utf-8")));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void connectionServer() {
        try {
            while (true) {
                selector.select(2000);
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    if (key.isReadable()) {
                        SocketChannel channel = (SocketChannel) key.channel();
                        ByteBuffer byteBuffer = ByteBuffer.allocate(1024);
                        channel.read(byteBuffer);

                        System.out.println(new String(byteBuffer.array(), "utf-8"));
                    }
                    // 一定得删除,不然会永远处理重复的事件
                    keyIterator.remove();
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public static void main(String[] args) {
        GroupChatClient groupChatClient = new GroupChatClient();
        new Thread(groupChatClient::connectionServer).start();

        Scanner in = new Scanner(System.in);
        while (in.hasNext()) {
            String message = in.nextLine();
            System.out.println("发送消息: " + message);
            groupChatClient.sendMessage(message);
        }
    }


}
