package com.chuanchuan.seata.storage.service;

import com.chuanchuan.seata.storage.dao.StorageDAO;
import com.chuanchuan.seata.storage.vo.StorageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class StorageServiceImpl implements StorageService {
    @Autowired
    private StorageDAO dao;

    @Override
    //RM
    public String saveStorage(StorageModel sm) {
        dao.saveStorage(sm);
        return "okok";
    }

}
