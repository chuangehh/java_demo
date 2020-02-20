package com.lcc.nio.groupchat;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;

/**
 * 群聊系统: 服务端
 * <p>
 * 功能:
 * 1.监听一个地址
 * 2.处理客户端上/下线
 * 3.实现客户端发送数据 转发到其他客户端
 *
 * @author liangchuanchuan
 */
public class GroupChatServer {
    private ServerSocketChannel server;
    private Selector selector;
    private String host = "127.0.0.1";
    private int port = 1010;

    /**
     * 服务器启动监听
     */
    private void listen() {
        try {
            // 1.监听一个地址
            selector = Selector.open();
            server = ServerSocketChannel.open();
            server.socket().bind(new InetSocketAddress(host, port));
            server.configureBlocking(false);
            server.register(selector, SelectionKey.OP_ACCEPT);
            System.out.println("服务端启动开始监听");

            loopEvent();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 轮询selector事件并处理
     */
    private void loopEvent() {
        while (true) {
            try {
                // System.out.println("轮询selector事件并处理");
                selector.select();
                Iterator<SelectionKey> keyIterator = selector.selectedKeys().iterator();
                while (keyIterator.hasNext()) {
                    SelectionKey key = keyIterator.next();
                    // 链接
                    if (key.isAcceptable()) {
                        SocketChannel channel = server.accept();
                        channel.configureBlocking(false);
                        System.out.println("(key.channel() == server): " + (key.channel() == server));

                        channel.register(selector, SelectionKey.OP_READ);
                        System.out.println("客户端" + channel.getRemoteAddress() + " 上线");
                    }
                    // 服务端接收到消息
                    if (key.isReadable()) {
                        handlerBussince(key);
                    }
                    // 一定得删除,不然会永远处理重复的事件
                    keyIterator.remove();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 处理业务
     * 1.服务端打印客户端的消息
     * 2.服务端转发消息到其他客户端
     *
     * @param key
     */
    private void handlerBussince(SelectionKey key) {
        SocketChannel channel = (SocketChannel) key.channel();
        try {
            ByteBuffer buffer = ByteBuffer.allocate(1024);
            channel.read(buffer);
            String message = new String(buffer.array(), "utf-8");
            System.out.println("客户端" + channel.getRemoteAddress() + " 发送消息:" + message);
            // 转发到其他客户端
            sendOtherClient(channel, message);
        } catch (IOException e) {
            key.cancel();
            try {
                System.out.println("客户端" + channel.getRemoteAddress() + " 下线");
            } catch (IOException e1) {
                e1.printStackTrace();
            }
            e.printStackTrace();
        }
    }

    /**
     * 把消息发送到其他客户端
     *
     * @param self    当前channel
     * @param message 当前channel发送的消息
     */
    private void sendOtherClient(SocketChannel self, String message) {
        try {
            for (SelectionKey selectionKey : selector.keys()) {
                if (selectionKey.channel() instanceof SocketChannel && selectionKey.channel() != self) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    channel.write(ByteBuffer.wrap(message.getBytes("utf-8")));
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        GroupChatServer groupChatServer = new GroupChatServer();
        groupChatServer.listen();
    }

}
