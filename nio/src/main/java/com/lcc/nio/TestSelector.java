package com.lcc.nio;

import org.junit.Test;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.nio.ByteBuffer;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Iterator;
import java.util.Set;

/**
 * 测试Selector
 *
 * 不要用debug运行,服务端会无限打印很多次,原因未知
 *
 * @author liangchuanchuan
 */
public class TestSelector {

    /**
     * 服务端
     */
    @Test
    public void server() throws IOException {
        // 1.serverSocketChannel注册OP_ACCEPT事件到selector
        Selector selector = Selector.open();
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open().bind(new InetSocketAddress("127.0.0.1", 1001));
        // 配置非阻塞? 我也不知道为啥,不配就报错,后面在研究
        serverSocketChannel.configureBlocking(false);
        serverSocketChannel.register(selector, SelectionKey.OP_ACCEPT);

        while (true) {
            // 2.selector 阻塞获取事件
            if (selector.select(2000) == 0) {
                System.out.println("服务端等待2秒,无客户端链接");
                continue;
            }
            // 3.获取不同类型事件分别处理
            Set<SelectionKey> selectionKeys = selector.selectedKeys();
            Iterator<SelectionKey> keyIterator = selectionKeys.iterator();
            while (keyIterator.hasNext()) {
                SelectionKey selectionKey = keyIterator.next();
                // 3.1 处理OP_ACCEPT 事件
                if (selectionKey.isAcceptable()) {
                    SocketChannel channel = serverSocketChannel.accept();
                    // 配置非阻塞? 我也不知道为啥,不配就报错,后面在研究
                    channel.configureBlocking(false);
                    channel.register(selector, SelectionKey.OP_READ, ByteBuffer.allocate(1024));
                    System.out.println("当前OP_ACCEPT 链接SocketChannel:" + channel.hashCode());
                }
                // 3.2 处理OP_READ 事件
                else if (selectionKey.isReadable()) {
                    SocketChannel channel = (SocketChannel) selectionKey.channel();
                    ByteBuffer buffer = (ByteBuffer) selectionKey.attachment();
                    channel.read(buffer);
                    System.out.println(new String(buffer.array(), "utf-8"));
                    System.out.println("当前OP_REA 链接SocketChannel:" + channel.hashCode());
                }
                // 4.处理完事件,需要删除当前事件
                keyIterator.remove();
            }
        }

    }


    /**
     * 客户端
     */
    @Test
    public void client() throws IOException {
        SocketChannel socketChannel = SocketChannel.open();

        if (!socketChannel.connect(new InetSocketAddress("127.0.0.1", 1001))) {
            while (!socketChannel.finishConnect()) {
                System.out.println("客户端如果未链接可以去做其他事情");
            }
        }

        socketChannel.write(ByteBuffer.wrap("你好,netty".getBytes("utf-8")));
        System.in.read();
    }


}
