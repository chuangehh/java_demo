package com.lcc.bio;

import java.io.IOException;
import java.io.InputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * BIO编程实践
 *
 * @author liangchuanchuan
 */
public class BIOServer {
    public static void main(String[] args) {
        try {
            // 1.启动Server并监听6666端口
            ServerSocket serverSocket = new ServerSocket(6666);
            ExecutorService cachedThreadPool = Executors.newCachedThreadPool();

            while (true) {
                // 2.阻塞获取客户端连接
                System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().getId() + " 阻塞获取客户端连接");
                Socket accept = serverSocket.accept();
                System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().getId() + " 获取客户端连接");

                // 3.使用线程处理客户端的请求
                cachedThreadPool.execute(() -> {
                    try {
                        // 3.1 阻塞获取客户端输入流
                        InputStream inputStream = accept.getInputStream();
                        byte[] bytes = new byte[1024];
                        // 3.2 不断阻塞读取客户端的请求
                        while (true) {
                            System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().getId() + " 阻塞读取客户端的请求...");
                            int read = inputStream.read(bytes);
                            System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().getId() + " 读取客户端的请求...");
                            // 3.3 控制台输出客户端的请求数据
                            System.out.println(new String(bytes));

                            if (read == -1) {
                                break;
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                });
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
