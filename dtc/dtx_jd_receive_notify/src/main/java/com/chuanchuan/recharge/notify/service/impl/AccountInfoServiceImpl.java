package com.chuanchuan.recharge.notify.service.impl;

import com.chuanchuan.recharge.notify.api.RechargeClient;
import com.chuanchuan.recharge.notify.dao.AccountInfoDao;
import com.chuanchuan.recharge.notify.entity.AccountRecharge;
import com.chuanchuan.recharge.notify.message.model.AccountChangeEvent;
import com.chuanchuan.recharge.notify.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;

@Service
@Slf4j
public class AccountInfoServiceImpl implements AccountInfoService {
    @Resource
    AccountInfoDao accountInfoDao;

    @Resource
    RechargeClient rechargeClient;

    //更新账户金额
    @Override
    @Transactional
    public void updateAccountBalance(AccountChangeEvent accountChange) {
        //幂等校验
        if (accountInfoDao.isExistTx(accountChange.getTxNo()) > 0) {
            return;
        }
        int i = accountInfoDao.updateAccountBalance(accountChange.getAccountNo(), accountChange.getAmount());
        //插入事务记录，用于幂等控制
        accountInfoDao.addTx(accountChange.getTxNo());
    }

    //远程调用查询充值结果,自己京东侧发起查询，调用中国移动充值系统充值情况
    @Override
    public AccountRecharge queryRechargeResult(String tx_no) {
        //远程调用
        AccountRecharge accountRecharge = rechargeClient.rechargeresult(tx_no);

        if (accountRecharge != null && "success".equals(accountRecharge.getResult())) {
            //更新账户金额
            AccountChangeEvent accountChangeEvent = new AccountChangeEvent();
            accountChangeEvent.setAccountNo(accountRecharge.getAccountNo());//账号
            accountChangeEvent.setAmount(accountRecharge.getRechargeAmount());//充值金额
            accountChangeEvent.setTxNo(accountRecharge.getId());//充值事务号

            updateAccountBalance(accountChangeEvent);
        }
        return accountRecharge;
    }
}

