package com.chuanchuan.recharge.notify.api;

import com.chuanchuan.recharge.notify.entity.AccountRecharge;
import org.springframework.stereotype.Component;

@Component
public class RechargeFallback implements RechargeClient {
    @Override
    public AccountRecharge rechargeresult(String txNo) {
        return new AccountRecharge(txNo, "1", 0, "服务降级，没有对应记录或超时");
    }
}
