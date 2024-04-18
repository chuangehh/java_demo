package com.chuanchuan.bank2.message;

import com.alibaba.fastjson.JSONObject;
import com.chuanchuan.bank2.message.model.AccountChangeEvent;
import com.chuanchuan.bank2.service.AccountInfoService;
import org.apache.rocketmq.spring.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * MQ消费监听者
 */
@Component
@RocketMQMessageListener(consumerGroup = "consumer_dtx_bank2", topic = "dtx_bank_topic")
public class ComsumerMsgListener implements RocketMQListener<String> {


    @Autowired
    private AccountInfoService accountInfoService;

    @Override
    public void onMessage(String message) {
        AccountChangeEvent event = JSONObject.parseObject(message, AccountChangeEvent.class);
        // int i = 10 / 0;
        accountInfoService.doTransfer(event);
    }


}
