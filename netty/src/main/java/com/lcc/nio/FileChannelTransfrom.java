package com.lcc.nio;

import java.io.*;
import java.nio.channels.FileChannel;

public class FileChannelTransfrom {

    public static void main(String[] args) throws IOException {
        FileInputStream inputStream = new FileInputStream("D:\\javaProject\\java_demo\\netty\\src\\main\\resources\\美臭蛋.jpg");
        FileOutputStream outputStream = new FileOutputStream("D:\\javaProject\\java_demo\\netty\\src\\main\\resources\\美臭蛋_copy.jpg");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        outputChannel.transferFrom(inputChannel, 0, inputChannel.size());

        // close
        inputChannel.close();
        outputChannel.close();

        inputStream.close();
        outputStream.close();
    }
}
