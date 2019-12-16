package com.atlcc.distributed.lock;

public class Client {

    public static void main(String[] args) {

        for (int i = 0; i < 20; i++) {
            new Thread(() -> {
                new OrderService().order();
            }, "order-" + i).start();
        }

    }

}
