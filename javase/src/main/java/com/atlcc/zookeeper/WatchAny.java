package com.atlcc.zookeeper;

import org.I0Itec.zkclient.IZkDataListener;
import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WatchAny {

	private final static ZkClient ZK_CLIENT = new ZkClient("hadoop100:2181", 45 * 1000);
	private final static String PATH = "/atlcc";

	private void listen(String listenPath) {

		ZK_CLIENT.subscribeDataChanges(listenPath, new IZkDataListener() {
			@Override
			public void handleDataChange(String path, Object o) throws Exception {
				System.out.println(Thread.currentThread().getName() + " " + o);
			}

			@Override
			public void handleDataDeleted(String path) throws Exception {
				System.out.println(Thread.currentThread().getName() + " " + path);
			}
		});
	}

	private void create() {
		if (!ZK_CLIENT.exists(PATH)) {
			ZK_CLIENT.create(PATH, "lcc01", CreateMode.PERSISTENT);
		}
		listen(PATH);
	}

	private void update(String value) {
		ZK_CLIENT.writeData(PATH, value);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		WatchAny watch = new WatchAny();
		watch.create();

		watch.update("value1");
		TimeUnit.SECONDS.sleep(1);
		watch.update("value2");
		TimeUnit.SECONDS.sleep(1);
		watch.update("value3");

		System.in.read();
	}

}
