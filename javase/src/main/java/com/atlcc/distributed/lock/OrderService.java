package com.atlcc.distributed.lock;

public class OrderService {

    ZkLock zkLock = new ZkLockImpl();

    /**
     * 下单
     */
    public void order() {
        zkLock.lock();
        try {
            String orderNumber = OrderNumCreateUtil.getOrderNumber();
            System.out.println(orderNumber);
        }finally {
            zkLock.unLock();
        }

    }
}
