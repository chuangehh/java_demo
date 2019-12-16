package com.atlcc.distributed.lock;

import org.I0Itec.zkclient.IZkDataListener;

import java.util.concurrent.CountDownLatch;

public class ZkLockImpl extends ZkAbstractLock {
    CountDownLatch countDownLatch;

    @Override
    public boolean tryZkLock() {
        try {
            zkClient.createEphemeral(ZK_LOCK_PATH);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public void waitLock() {
        IZkDataListener iZkDataListener = new IZkDataListener() {
            @Override
            public void handleDataChange(String dataPath, Object data) throws Exception {
            }

            @Override
            public void handleDataDeleted(String dataPath) throws Exception {
                if(countDownLatch != null){
                    countDownLatch.countDown();
                }
            }
        };

        zkClient.subscribeDataChanges(ZK_LOCK_PATH, iZkDataListener);
        if(zkClient.exists(ZK_LOCK_PATH)){
            countDownLatch = new CountDownLatch(1);
            try {
                countDownLatch.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            zkClient.unsubscribeDataChanges(ZK_LOCK_PATH, iZkDataListener);
        }
    }
}
