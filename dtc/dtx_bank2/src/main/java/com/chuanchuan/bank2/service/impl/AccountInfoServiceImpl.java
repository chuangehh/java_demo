package com.chuanchuan.bank2.service.impl;

import com.chuanchuan.bank2.dao.AccountInfoDao;
import com.chuanchuan.bank2.message.model.AccountChangeEvent;
import com.chuanchuan.bank2.service.AccountInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    AccountInfoDao accountInfoDao;

    @Transactional
    @Override
    public void doTransfer(AccountChangeEvent accountChangeEvent) {
        // 1、幂等判断
        int countNum = accountInfoDao.existsTxNo(accountChangeEvent.getTxNo());
        if (countNum >= 1) {
            return;
        }

        // 2、插入幂等数据
        accountInfoDao.insertTxNo(accountChangeEvent.getTxNo());

        // 3、修改转账  给2号账户转账
        accountChangeEvent.setAccountNo("2");
        double amount = accountChangeEvent.getAmount();
        accountInfoDao.updateUserAccount(accountChangeEvent.getAccountNo(), amount);

        // 4、故意转坏账，抛个异常，看看是否执行转账
        if (accountChangeEvent.getAmount() == 4.4) {
            System.out.println(10 / 0);
        }
    }

}
