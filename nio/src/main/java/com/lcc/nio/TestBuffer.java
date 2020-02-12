package com.lcc.nio;

import org.junit.Test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.net.InetSocketAddress;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.nio.MappedByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.ServerSocketChannel;
import java.nio.channels.SocketChannel;
import java.util.Arrays;

/**
 * 测试Buffer
 * 0 <= mark <= position <= limit <= capacity
 *
 * @author liangchuanchuan
 */
public class TestBuffer {


    /**
     * 分散/聚合
     * <p>
     * Scattering: 将数据写入到buffer时,可以采用buffer数组,依次写入
     * Gathering: 从buffer读取数据时,可以使用buffer数组,依次读入
     */
    @Test
    public void scatteringAndGathering() throws IOException {
        ServerSocketChannel serverSocketChannel = ServerSocketChannel.open().bind(new InetSocketAddress(7070));
        SocketChannel socketChannel = serverSocketChannel.accept();

        ByteBuffer[] byteBuffers = new ByteBuffer[2];
        byteBuffers[0] = ByteBuffer.allocate(5);
        byteBuffers[1] = ByteBuffer.allocate(3);

        // 循环读取
        int maxMessageSize = 8;
        while (true) {

            //数据读取
            int byteReadSize = 0;
            while (byteReadSize < maxMessageSize) {
                long read = socketChannel.read(byteBuffers);
                byteReadSize += read;
                Arrays.stream(byteBuffers).forEach(System.out::println);
            }

            //数据写出
            Arrays.stream(byteBuffers).forEach(Buffer::flip);
            int byteWriteSize = 0;
            while (byteWriteSize < maxMessageSize) {
                long write = socketChannel.write(byteBuffers);
                byteWriteSize += write;
            }

            System.out.println("byteReadSize:" + byteReadSize + " byteWriteSize:" + byteWriteSize);
            Arrays.stream(byteBuffers).forEach(Buffer::clear);
        }

    }


    /**
     * 直接映射到内存,减少一次用户态到系统态的拷贝
     *
     * @throws IOException
     */
    @Test
    public void mappedByteBuffer() throws IOException {
        RandomAccessFile rw = new RandomAccessFile("nio_file_channel.out", "rw");
        FileChannel channel = rw.getChannel();


        MappedByteBuffer mappedByteBuffer = channel.map(FileChannel.MapMode.READ_WRITE, 0, 30);
        byte[] bytes = "臭蛋蛋".getBytes("utf-8");
        mappedByteBuffer.put(bytes, 0, bytes.length);

        rw.close();
    }

    /**
     * readOnly Buffer不可写
     */
    @Test
    public void bufferReadOnly() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.putInt(100);

        ByteBuffer readOnlyBuffer = buffer.asReadOnlyBuffer();
        readOnlyBuffer.flip();
        System.out.println(readOnlyBuffer.getInt());
        // java.nio.ReadOnlyBufferException
        readOnlyBuffer.putInt(200);
    }

    /**
     * 放入/读取类型应一致
     */
    @Test
    public void bufferPutGetType() {
        ByteBuffer buffer = ByteBuffer.allocate(1024);

        buffer.putInt(100);

        buffer.flip();

        // System.out.println(buffer.getInt());
        // java.nio.BufferUnderflowException
        System.out.println(buffer.getDouble());
    }

    /**
     * 测试api
     */
    @Test
    public void apis() {
        String str = "abcde";

        // 0.获取Buffer
        ByteBuffer buf = ByteBuffer.allocate(1024);
        // ByteBuffer buf = ByteBuffer.allocateDirect(1024);
        System.out.println("===================allocate()===================");
        System.out.println("buf.position(): " + buf.position());
        System.out.println("buf.limit(): " + buf.limit());
        System.out.println("buf.capacity(): " + buf.capacity());

        // 1.put()
        buf.put(str.getBytes());
        System.out.println("===================put()===================");
        System.out.println("buf.position(): " + buf.position());
        System.out.println("buf.limit(): " + buf.limit());
        System.out.println("buf.capacity(): " + buf.capacity());

        // 2.flip() 切换读模式
        buf.flip();
        System.out.println("===================flip()===================");
        System.out.println("buf.position(): " + buf.position());
        System.out.println("buf.limit(): " + buf.limit());
        System.out.println("buf.capacity(): " + buf.capacity());

        // 3.get() 读取
        byte[] bytes = new byte[buf.limit()];
        buf.get(bytes);
        System.out.println("===================get()===================");
        System.out.println(new String(bytes));
        System.out.println("buf.position(): " + buf.position());
        System.out.println("buf.limit(): " + buf.limit());
        System.out.println("buf.capacity(): " + buf.capacity());

        // 4.resind() 重新读取
        buf.rewind();
        System.out.println("===================rewind()===================");
        System.out.println("buf.position(): " + buf.position());
        System.out.println("buf.limit(): " + buf.limit());
        System.out.println("buf.capacity(): " + buf.capacity());

        // 5.mark() reset()
        bytes = new byte[2];
        buf.get(bytes);
        System.out.println(new String(bytes));

        // position:2
        buf.mark();
        // position:4
        buf.get(bytes);
        System.out.println(new String(bytes));
        // position:2
        buf.reset();
        buf.get(bytes);
        System.out.println(new String(bytes));

        System.out.println("===================mark() reset()===================");
        System.out.println("buf.position(): " + buf.position());
        System.out.println("buf.limit(): " + buf.limit());
        System.out.println("buf.capacity(): " + buf.capacity());

        // 6.clear()
        buf.clear();
        System.out.println("===================clear()===================");
        bytes = new byte[buf.limit()];
        buf.get(bytes);
        System.out.println(new String(bytes));
        System.out.println("buf.position(): " + buf.position());
        System.out.println("buf.limit(): " + buf.limit());
        System.out.println("buf.capacity(): " + buf.capacity());


        // 7.hasArray
        System.out.println(buf.hasArray());
        // 8.array
        System.out.println("array: " + new String(buf.array()));
    }

}
