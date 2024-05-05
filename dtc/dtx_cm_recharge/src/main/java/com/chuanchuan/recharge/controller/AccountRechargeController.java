package com.chuanchuan.recharge.controller;

import com.chuanchuan.recharge.entity.AccountRecharge;
import com.chuanchuan.recharge.service.AccountRechargeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.UUID;

@RestController
public class AccountRechargeController {
    @Resource
    AccountRechargeService accountRechargeService;

    //充值，测试地址：http://localhost:4444/recharge?accountNo=1&rechargeAmount=1
    @GetMapping(value = "/recharge")
    public AccountRecharge recharge(AccountRecharge accountRecharge) {
        //生成事务编号
        accountRecharge.setId(UUID.randomUUID().toString());
        return accountRechargeService.insertAccountPay(accountRecharge);
    }

    //查询充值结果，测试地址：http://localhost:4444/rechargeresult/80ceda6e-0c33-4e59-ab84-83c492d68c0c
    @GetMapping(value = "/rechargeresult/{txNo}")
    public AccountRecharge rechargeresult(@PathVariable("txNo") String txNo) {
        return accountRechargeService.getAccountPay(txNo);
    }
}