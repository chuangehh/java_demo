package com.lcc.jvm.oom;

import sun.misc.VM;

import java.nio.ByteBuffer;

/**
 * -XX:MaxDirectMemorySize=5m -XX:+PrintGCDetails
 *
 * @author liangchuanchuan
 */
public class DirectBufferMemoryDemo {

	public static void main(String[] args) {
		// jvm可以使用的本地内存 默认OS 1/4
		System.out.println(VM.maxDirectMemory() / 1024 / 1024 + "MB");

		ByteBuffer byteBuffer = ByteBuffer.allocateDirect(1024 * 1024 * 10);
	}

}
