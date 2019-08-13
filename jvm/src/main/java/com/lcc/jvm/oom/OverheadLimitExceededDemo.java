package com.lcc.jvm.oom;

import java.util.HashMap;
import java.util.Random;

/**
 * -Xms20m -Xmx20m -XX:+PrintGCDetails
 *
 * @author liangchuanchuan
 */
public class OverheadLimitExceededDemo {

	public static void main(String[] args) {
		HashMap<Integer, String> hashMap = new HashMap<>();
		Random random = new Random();
		while (true) {
			int i = random.nextInt();
			hashMap.put(i, "value");
		}


	}

}
