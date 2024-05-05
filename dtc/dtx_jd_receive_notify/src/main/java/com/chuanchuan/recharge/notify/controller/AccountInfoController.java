package com.chuanchuan.recharge.notify.controller;

import com.chuanchuan.recharge.notify.entity.AccountRecharge;
import com.chuanchuan.recharge.notify.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
public class AccountInfoController {

    @Autowired
    private AccountInfoService accountInfoService;

    //主动查询充值结果,测试地址:  http://localhost:3333/queryRecharge/c85204c6-f4bd-4f24-b984-b180be91d8dc
    @GetMapping(value = "/queryRecharge/{txNo}")
    public AccountRecharge queryRechargeResult(@PathVariable("txNo") String txNo) {
        log.info("----京东侧自己查询流水号:{}", txNo);
        return accountInfoService.queryRechargeResult(txNo);
    }
}
