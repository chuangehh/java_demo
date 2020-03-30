package com.atlcc.zookeeper;

import org.I0Itec.zkclient.ZkClient;
import org.apache.zookeeper.CreateMode;

import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class WatchChild {

	private final static ZkClient ZK_CLIENT = new ZkClient("hadoop100:2181", 45 * 1000);
	private final static String PATH = "/atlcc_child";

	private void listen(String listenPath) {
		ZK_CLIENT.subscribeChildChanges(listenPath, (parentPath, currentChilds) -> {
			System.out.println(Thread.currentThread().getName() + " parentPath:" + parentPath);
			System.out.println(Thread.currentThread().getName() + " currentChilds:" + currentChilds);
		});
	}

	private void create() {
		if (!ZK_CLIENT.exists(PATH)) {
			ZK_CLIENT.create(PATH, "lcc01", CreateMode.PERSISTENT);
		}
		listen(PATH);
	}

	public static void main(String[] args) throws IOException, InterruptedException {
		WatchChild watch = new WatchChild();
		watch.create();

		watch.addChild("01", "01");
		TimeUnit.SECONDS.sleep(1);

		watch.addChild("02", "02");
		TimeUnit.SECONDS.sleep(1);

		watch.addChild("03", "03");
		TimeUnit.SECONDS.sleep(1);

		watch.deleteChild("03");

		System.in.read();
	}

	private void deleteChild(String path) {
		String childPath = PATH + "/" + path;
		if (ZK_CLIENT.exists(childPath)) {
			ZK_CLIENT.delete(childPath);
		}
	}

	private void addChild(String path, String value) {
		String childPath = PATH + "/" + path;
		if (!ZK_CLIENT.exists(childPath)) {
			ZK_CLIENT.create(childPath, value, CreateMode.PERSISTENT);
		}
	}

}
