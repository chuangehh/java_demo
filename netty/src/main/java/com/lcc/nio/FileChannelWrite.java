package com.lcc.nio;

import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileChannelWrite {

    public static void main(String[] args) throws IOException {

        // 1、字符串转成字节，便于磁盘和网络传输
        byte[] bytes = "我爱媳妇".getBytes(StandardCharsets.UTF_8);
        System.out.println(bytes.length);

        // 2、文件输出位置
        FileOutputStream outputStream = new FileOutputStream("D:\\javaProject\\java_demo\\netty\\fileChannel.txt");


        // 3、数据丢给buffer，然后用管道写出去
        FileChannel channel = outputStream.getChannel();
        channel.write((ByteBuffer) ByteBuffer.allocate(bytes.length).put(bytes).flip());

        outputStream.close();
    }

}
