package com.chuanchuan.bank1.controller;

import com.chuanchuan.bank1.message.model.AccountChangeEvent;
import com.chuanchuan.bank1.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.UUID;

@RestController
public class AccountInfoController {

    @Autowired
    private AccountInfoService accountInfoService2;

    @RequestMapping("/transfer")
    public String transfer(String accountNo, Double amount) {
        String txNo = UUID.randomUUID().toString();
        accountInfoService2.transferMQ(new AccountChangeEvent(accountNo, amount, txNo));
        return "转账成功" + amount + " txNo:" + txNo;
    }

}