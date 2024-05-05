package com.chuanchuan.recharge.service;

import com.chuanchuan.recharge.entity.AccountRecharge;

public interface AccountRechargeService {
    //充值
    public AccountRecharge insertAccountPay(AccountRecharge accountRecharge);

    //查询充值结果
    public AccountRecharge getAccountPay(String txNo);
}
