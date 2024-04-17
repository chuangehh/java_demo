package com.chuanchuan.bank1.message;

import com.alibaba.fastjson.JSONObject;
import com.chuanchuan.bank1.dao.AccountInfoDao;
import com.chuanchuan.bank1.message.model.AccountChangeEvent;
import com.chuanchuan.bank1.service.AccountInfoService;
import org.apache.rocketmq.spring.annotation.RocketMQTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionListener;
import org.apache.rocketmq.spring.core.RocketMQLocalTransactionState;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.Message;
import org.springframework.stereotype.Component;

/**
 * MQ消费监听者
 */
@Component
@RocketMQTransactionListener(txProducerGroup = "dtx_bank1")
public class ProducerTxmsgListener implements RocketMQLocalTransactionListener {

    @Autowired
    private AccountInfoService accountInfoService;
    @Autowired
    AccountInfoDao accountInfoDao;

    @Override
    public RocketMQLocalTransactionState executeLocalTransaction(Message message, Object o) {
        try {
            AccountChangeEvent event = JSONObject.parseObject(new String((byte[]) message.getPayload()), AccountChangeEvent.class);
            accountInfoService.doTransfer(event);
            return RocketMQLocalTransactionState.COMMIT;
        } catch (Exception e) {
            e.printStackTrace();
            return RocketMQLocalTransactionState.ROLLBACK;
        }

    }

    @Override
    public RocketMQLocalTransactionState checkLocalTransaction(Message message) {
        AccountChangeEvent event = JSONObject.parseObject(new String((byte[]) message.getPayload()), AccountChangeEvent.class);
        int countNum = accountInfoDao.existsTxNo(event.getTxNo());
        if (countNum >= 1) {
            return RocketMQLocalTransactionState.COMMIT;
        } else {
            return RocketMQLocalTransactionState.ROLLBACK;
        }
    }
}
