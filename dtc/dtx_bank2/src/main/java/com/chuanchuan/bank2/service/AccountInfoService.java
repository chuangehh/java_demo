package com.chuanchuan.bank2.service;

import com.chuanchuan.bank2.message.model.AccountChangeEvent;

public interface AccountInfoService {


    void doTransfer(AccountChangeEvent accountChangeEvent);


}
