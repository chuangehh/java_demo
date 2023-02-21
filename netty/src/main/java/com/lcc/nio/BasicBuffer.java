package com.lcc.nio;

import java.nio.IntBuffer;

public class BasicBuffer {
    public static void main(String[] args) {
        IntBuffer intBuffer = IntBuffer.allocate(5);

//        intBuffer.put(1);
//        intBuffer.put(2);
//        intBuffer.put(3);
//        intBuffer.put(4);
//        intBuffer.put(5);

        for (int i = 0; i < 4; i++) {
            intBuffer.put(i * 2);
        }

        intBuffer.flip();
        intBuffer.position(0);
        System.out.println(intBuffer.isReadOnly());

        while (intBuffer.hasRemaining()) {
            System.out.println(intBuffer.get());
        }

        System.out.println("=======");
        for (int i : intBuffer.array()) {
            System.out.println(i);
        }


    }
}
