package com.chuanchuan.recharge.service.impl;

import com.chuanchuan.recharge.dao.AccountRechargeDao;
import com.chuanchuan.recharge.entity.AccountRecharge;
import com.chuanchuan.recharge.service.AccountRechargeService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

@Service
public class AccountRechargeServiceImpl implements AccountRechargeService {

    @Resource
    AccountRechargeDao accountRechargeDao;
    @Resource
    RocketMQTemplate rocketMQTemplate;

    @Override
    public AccountRecharge insertAccountPay(AccountRecharge accountRecharge) {
        int success = accountRechargeDao.insertAccountRecharge(accountRecharge.getId(), accountRecharge.getAccountNo(), accountRecharge.getRechargeAmount(), "success");
        if (success > 0) {
            //发送通知,使用普通消息发送通知
            accountRecharge.setResult("success");
            rocketMQTemplate.convertAndSend("topic_notifymsg", accountRecharge);
            return accountRecharge;
        }
        return null;
    }

    @Override
    public AccountRecharge getAccountPay(String txNo) {
        return accountRechargeDao.findByIdTxNo(txNo);
    }

}
