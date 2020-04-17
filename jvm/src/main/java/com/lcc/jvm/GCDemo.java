package com.lcc.jvm;

import java.util.HashMap;
import java.util.Random;

/**
 * // 新生代
 * 1. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseSerialGC (def new + tenured)
 * 2. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParNewGC (par new + tenured)
 *    jdk8: Java HotSpot(TM) 64-Bit Server VM warning: Using the ParNew young collector with the Serial old collector is deprecated and will likely be removed in a future release
 *    使用UseParNewGC 会自动关联老年代 UseSerialOldGC
 *
 * 3. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelGC (PSYoungGen + ParOldGen)
 *
 * // 老年代
 * 4. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseParallelOldGC (PSYoungGen + ParOldGen)
 * 5. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseConcMarkSweepGC (par new + concurrent mark-sweep)
 *
 * // 横夸两代
 * 6. -Xms10m -Xmx10m -XX:+PrintGCDetails -XX:+PrintCommandLineFlags -XX:+UseG1GC (garbage-first)
 *
 *
 * gc log
 *
 * -verbose:gc -XX:+PrintGC -XX:+PrintGCDetails -XX:+PrintGCApplicationStoppedTime -XX:+PrintHeapAtGC -XX:+PrintGCDateStamps -XX:+PrintAdaptiveSizePolicy -XX:+PrintTenuringDistribution -XX:+PrintSafepointStatistics -XX:PrintSafepointStatisticsCount=1 -XX:PrintFLSStatistics=1 -Xloggc:/var/log/jvm/GCDemo.gc -XX:+UseGCLogFileRotation -XX:NumberOfGCLogFiles=10 -XX:GCLogFileSize=64M
 *
 * @author liangchuanchuan
 */
public class GCDemo {

	public static void main(String[] args) {
		HashMap<Integer, String> hashMap = new HashMap<>();
		Random random = new Random();
		while (true) {
			int i = random.nextInt();
			hashMap.put(i, "value");
		}
	}

}
