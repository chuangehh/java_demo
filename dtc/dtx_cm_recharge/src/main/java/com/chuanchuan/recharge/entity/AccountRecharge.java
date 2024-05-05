package com.chuanchuan.recharge.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AccountRecharge implements Serializable {
    //事务号
    private String id;
    //账号
    private String accountNo;
    //充值金额
    private double rechargeAmount;
    //充值结果
    private String result;
}

