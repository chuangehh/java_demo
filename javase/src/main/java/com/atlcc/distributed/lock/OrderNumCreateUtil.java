package com.atlcc.distributed.lock;

import java.util.concurrent.TimeUnit;

public class OrderNumCreateUtil {

    static int num;

    public static String getOrderNumber() {
        int tmp = num+1;
        try {
            TimeUnit.MILLISECONDS.sleep(2);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        num = tmp;
        return "生成订单号:" + num;
    }

}
