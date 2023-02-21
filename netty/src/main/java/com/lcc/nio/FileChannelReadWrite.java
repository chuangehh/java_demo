package com.lcc.nio;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

public class FileChannelReadWrite {

    public static void main(String[] args) throws IOException {
        // input
        FileInputStream inputStream = new FileInputStream("D:\\javaProject\\java_demo\\netty\\fileChannel.txt");
        FileChannel inputChannel = inputStream.getChannel();

        // output
        FileOutputStream outputStream = new FileOutputStream("D:\\javaProject\\java_demo\\netty\\fileChannelWrite.txt");
        FileChannel outputChannel = outputStream.getChannel();

        // buffer
        ByteBuffer byteBuffer = ByteBuffer.allocate(5);

        int read = 0;
        while (read != -1) {
            read = inputChannel.read(byteBuffer);
            System.out.println("read: " + read);

            outputChannel.write((ByteBuffer) byteBuffer.flip());

            // 如果不清理，第一次read 5, 后面read一直是0, 因为buffer满了
            byteBuffer.clear();
        }

        // close
        inputStream.close();
        outputStream.close();
    }

}
