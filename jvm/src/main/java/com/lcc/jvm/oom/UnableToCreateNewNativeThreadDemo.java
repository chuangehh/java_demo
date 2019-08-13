package com.lcc.jvm.oom;

/**
 * cat /etc/redhat-release
 * CentOS Linux release 7.6.1810 (Core)
 *
 * 警告: 使用非root用户,否则需要重启服务器
 * javac -d . UnableToCreateNewNativeThreadDemo.java
 * java com.lcc.jvm.oom.UnableToCreateNewNativeThreadDemo
 *
 * num: 4080
 *
 * cat /etc/security/limits.d/20-nproc.conf
 * *          soft    nproc     4096
 * root       soft    nproc     unlimited
 *
 * ==========================================
 * Windows 7 Administrator
 * num: 111862
 *
 * @author liangchuanchuan
 */
public class UnableToCreateNewNativeThreadDemo {

	public static void main(String[] args) {
		for (int i = 0; ; i++) {
			System.out.println("num: " + i);

			new Thread(() -> {
				try {
					Thread.sleep(Integer.MAX_VALUE);
				} catch (InterruptedException e) {
					e.printStackTrace();
				}
			}).start();
		}
	}
}
