package com.chuanchuan.recharge.notify.service;

import com.chuanchuan.recharge.notify.entity.AccountRecharge;
import com.chuanchuan.recharge.notify.message.model.AccountChangeEvent;

public interface AccountInfoService {

    //更新账户金额
    public void updateAccountBalance(AccountChangeEvent accountChange);

    //查询充值结果（远程调用）
    public AccountRecharge queryRechargeResult(String tx_no);

}


