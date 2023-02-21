package com.lcc.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 1、启动服务
 * 2、cmd: telnet 127.0.0.1 8888
 * 3、ctrl + ]
 * 4、send aaa    服务端可以接收到消息
 */
public class BioServer {


    public static void main(String[] args) throws IOException {
        ThreadPoolExecutor threadPool = new ThreadPoolExecutor(5, 10, 0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>(100), Executors.defaultThreadFactory(), new ThreadPoolExecutor.AbortPolicy());

        // 服务端绑定一个本地8888的端口号
        ServerSocket serverSocket = new ServerSocket(8888);

        while (true) {
            // 客服获取访问链接，当前只有一个客服，main主线程
            System.out.println(Thread.currentThread().getName() + " accept..");
            Socket socket = serverSocket.accept();
            System.out.println(Thread.currentThread().getName() + " " + socket.getInetAddress().getHostAddress() + " " + socket.getPort());

            // 把链接丢给线程池，工作人员能处理
            threadPool.execute(() -> {
                process(socket);
            });
        }
    }

    private static void process(Socket socket) {
        try {
            while (true) {
                // 服务端准备一个缓存区用于读取客户端的数据
                byte[] bytes = new byte[1024];
                InputStream inputStream = socket.getInputStream();

                System.out.println(Thread.currentThread().getName() + " read..");

                // 读取数据到内存种 bytes，1MB
                int read = inputStream.read(bytes);
                if (read == -1) {
                    break;
                }

                // 把客户端发送的数据打印
                System.out.println(Thread.currentThread().getName() + " " + new String(bytes, 0, read));
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }

        // 客户端断开链接
        System.out.println(Thread.currentThread().getName() + " 客户端断开链接");
    }
}