package com.lcc.nio;

import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * 用于源节点与目标节点的链接
 * <p>
 * java.nio.channels.Channel
 * |--FileChannel
 * |--SocketChannel
 * |--ServerSocketChannel
 * |--DatagramChannel
 * <p>
 * 获取通道方式
 * 1.通道类,提供的 getChannel()
 * 2.NIO.2 提供 open()静态
 * 3.Nio.2 Files 提供 newByteChannel()
 */
public class TestChannel {

    /**
     * 1.通过FileChannel写入数据
     */
    @Test
    public void fileChannelWrite() throws IOException {
        FileOutputStream fileOutputStream = new FileOutputStream("nio_file_channel.out");
        FileChannel channel = fileOutputStream.getChannel();

        // 数据
        String data = " * 获取通道方式\n" +
                " * 1.通道类,提供的 getChannel()\n" +
                " * 2.NIO.2 提供 open()静态\n" +
                " * 3.Nio.2 Files 提供 newByteChannel()\n";
        byte[] dataBytes = data.getBytes("utf-8");
        // Buffer
        ByteBuffer buffer = ByteBuffer.allocate(dataBytes.length);
        buffer.put(dataBytes);
        buffer.flip();
        // Channel
        channel.write(buffer);

        fileOutputStream.close();
    }

    /**
     * 2.通过FileChannel读入数据
     */
    @Test
    public void fileChannelRead() throws IOException {
        FileInputStream inputStream = new FileInputStream("nio_file_channel.out");
        FileChannel channel = inputStream.getChannel();

        // utf-8 3个byte一个中文
        ByteBuffer byteBuffer = ByteBuffer.allocate(3 * 10);
        while (true) {

            int read = channel.read(byteBuffer);
            if (read == -1) {
                break;
            }
            // 会出一个bug, 上一次迭代有部分数据在byteBuffer.hb 数组中, 若全部输出数据会多一截
            // String readData = new String(byteBuffer.array(), "utf-8");
            String readData = new String(byteBuffer.array(), 0, read, "utf-8");
            System.out.print(readData);
            byteBuffer.clear();
        }

        // 源码里关闭了channel
        inputStream.close();
    }

    /**
     * 3.通过Channel读取数据到Buffer中,在把这个Buffer数据写道另外一个文件
     *
     * @throws IOException
     */
    @Test
    public void fileChannelReadAndWriteUseSameBuffer() throws IOException {
        FileInputStream inputStream = new FileInputStream("nio_file_channel.out");
        FileOutputStream outputStream = new FileOutputStream("nio_file_channel_same_buffer.out");

        FileChannel inputChannel = inputStream.getChannel();
        FileChannel outputChannel = outputStream.getChannel();

        // 1.原始实现
//        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(3 * 10);
//        while (true) {
//            int read = inputChannel.read(byteBuffer);
//            if (read == -1) {
//                break;
//            }
//            byteBuffer.flip();
//            outputChannel.write(byteBuffer);
//            byteBuffer.clear();
//        }

        // 2.使用api
        outputChannel.transferFrom(inputChannel,0,inputChannel.size());

        inputStream.close();
        outputStream.close();
    }

}
