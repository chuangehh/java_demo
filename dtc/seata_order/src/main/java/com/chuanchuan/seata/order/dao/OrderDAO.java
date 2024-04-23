package com.chuanchuan.seata.order.dao;

import com.chuanchuan.seata.order.vo.OrderModel;

public interface OrderDAO {
    public String saveOrder(OrderModel sm);
}
