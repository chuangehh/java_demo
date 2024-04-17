package com.chuanchuan.bank1.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.chuanchuan.bank1.dao.AccountInfoDao;
import com.chuanchuan.bank1.message.model.AccountChangeEvent;
import com.chuanchuan.bank1.service.AccountInfoService;
import org.apache.rocketmq.spring.core.RocketMQTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AccountInfoServiceImpl implements AccountInfoService {

    @Autowired
    RocketMQTemplate rocketMqTemplate;
    @Autowired
    AccountInfoDao accountInfoDao;

    @Override
    public void transferMQ(AccountChangeEvent accountChangeEvent) {
        MessageBuilder<String> messageBuilder = MessageBuilder.withPayload(JSONObject.toJSONString(accountChangeEvent));
        rocketMqTemplate.sendMessageInTransaction("producer_dtx_bank1", "dtx_bank_topic", messageBuilder.build(), null);
    }

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

        // 3、修改转账
        double amount = accountChangeEvent.getAmount() * -1;
        accountInfoDao.updateUserAccount(accountChangeEvent.getAccountNo(), amount);

        // 4、故意转坏账，抛个异常，看看是否执行转账
        if (accountChangeEvent.getAmount() == 4 || accountChangeEvent.getAmount() == 44) {
            System.out.println(10 / 0);
        }
    }

}
