package com.chuanchuan.recharge.notify.api;

import com.chuanchuan.recharge.notify.entity.AccountRecharge;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(value = "dtx-cm-recharge", fallback = RechargeFallback.class)
public interface RechargeClient {
    //远程调用充值系统的接口查询充值结果
    @GetMapping(value = "/rechargeresult/{txNo}")
    public AccountRecharge rechargeresult(@PathVariable("txNo") String txNo);
}
