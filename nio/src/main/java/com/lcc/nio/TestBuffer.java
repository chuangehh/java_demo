package com.lcc.nio;

import org.junit.Test;

import java.nio.ByteBuffer;

/**
 * 测试Buffer
 * 0 <= mark <=position <= limit <= capacity
 */
public class TestBuffer {


    /**
     * 直接缓冲区,建立在操作系统内存中
     */
    @Test
    public void direct() {
        ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024);
        System.out.println(byteBuffer.isDirect());

        byteBuffer = ByteBuffer.allocate(1024);
        System.out.println(byteBuffer.isDirect());
    }

    /**
     * 测试api
     */
    @Test
    public void apis() {
        String str = "abcde";

        // 0.获取Buffer
        ByteBuffer buf = ByteBuffer.allocate(1024);
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

    }

}
