package com.chuanchuan.bank1.service;

import com.chuanchuan.bank1.message.model.AccountChangeEvent;

public interface AccountInfoService {

    /**
     * 1、向MQ发送转账消息
     * 然后等待MQ回调回调后检查幂等然后调用 transferDB() 2、向数据库提交
     * @param accountChangeEvent
     */
    void transferMQ(AccountChangeEvent accountChangeEvent);


    /**
     * 2、向数据库提交
     */
    void doTransfer(AccountChangeEvent accountChangeEvent);


}
