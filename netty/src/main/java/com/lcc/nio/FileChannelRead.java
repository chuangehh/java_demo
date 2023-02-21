package com.lcc.nio;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileChannelRead {

    public static void main(String[] args) throws IOException {

        // 1、文件读取位置
        File file = new File("D:\\javaProject\\java_demo\\netty\\fileChannel.txt");
        FileInputStream inputStream = new FileInputStream(file);


        // 2、数据丢给buffer，然后用管道写出去
        FileChannel channel = inputStream.getChannel();

        ByteBuffer byteBuffer = ByteBuffer.allocate((int) file.length());
        channel.read(byteBuffer);

        System.out.println(new String(byteBuffer.array(), StandardCharsets.UTF_8));

        inputStream.close();
    }

}
