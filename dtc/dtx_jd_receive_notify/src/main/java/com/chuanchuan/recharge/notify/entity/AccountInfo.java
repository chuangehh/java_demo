package com.chuanchuan.recharge.notify.entity;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountInfo implements Serializable {
    private Long id;
    private String accountName;
    private String accountNo;
    private String accountPassword;
    private Double accountBalance;
}