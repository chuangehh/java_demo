package com.chuanchuan.recharge.notify.message;

import com.alibaba.fastjson.JSON;
import com.chuanchuan.recharge.notify.entity.AccountRecharge;
import com.chuanchuan.recharge.notify.message.model.AccountChangeEvent;
import com.chuanchuan.recharge.notify.service.AccountInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
@Slf4j
@RocketMQMessageListener(topic = "topic_notifymsg", consumerGroup = "consumer_group_notifymsg_jd")
public class NotifyMsgListener implements RocketMQListener<AccountRecharge> {

    @Autowired
    AccountInfoService accountInfoService;

    //接收消息
    @Override
    public void onMessage(AccountRecharge accountRecharge) {
        log.info("京东侧，接收到了消息：{}", JSON.toJSONString(accountRecharge));

        if ("success".equals(accountRecharge.getResult())) {
            //更新账户金额
            AccountChangeEvent accountChangeEvent = new AccountChangeEvent();
            accountChangeEvent.setAccountNo(accountRecharge.getAccountNo());
            accountChangeEvent.setAmount(accountRecharge.getRechargeAmount());
            accountChangeEvent.setTxNo(accountRecharge.getId());

            accountInfoService.updateAccountBalance(accountChangeEvent);
        }

        log.info("京东侧，处理消息完成：{}", JSON.toJSONString(accountRecharge));
    }
}

