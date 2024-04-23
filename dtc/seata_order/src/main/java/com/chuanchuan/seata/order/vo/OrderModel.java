package com.chuanchuan.seata.order.vo;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_order")
public class OrderModel {
    @Id
    private String uuid;
    private String orderId;
    private float totalMoney;

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public float getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(float totalMoney) {
        this.totalMoney = totalMoney;
    }

    @Override
    public String toString() {
        return "OrderModel [uuid=" + uuid + ", orderId=" + orderId + ", totalMoney=" + totalMoney + "]";
    }

}
