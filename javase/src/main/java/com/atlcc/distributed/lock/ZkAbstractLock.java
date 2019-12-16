package com.atlcc.distributed.lock;

import org.I0Itec.zkclient.ZkClient;

public abstract class ZkAbstractLock implements ZkLock {

    protected ZkClient zkClient = new ZkClient("hadoop102:2181", 45 * 1000);
    protected static String ZK_LOCK_PATH = "/zkLock";

    @Override
    public void lock() {
        if (tryZkLock()) {
            System.out.println("占用锁成功");
        } else {
            waitLock();
            lock();
        }
    }

    public abstract boolean tryZkLock();

    public abstract void waitLock();

    @Override
    public void unLock() {
        if (zkClient != null) {
            zkClient.close();
        }
        System.out.println("释放锁成功");
    }

}
