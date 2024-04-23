package com.chuanchuan.seata.storage.controller;

import com.chuanchuan.seata.storage.api.StorageWebApi;
import com.chuanchuan.seata.storage.dto.StorageDTO;
import com.chuanchuan.seata.storage.service.StorageService;
import com.chuanchuan.seata.storage.vo.StorageModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class StorageServiceController implements StorageWebApi {

    @Autowired
    private StorageService service;


    /**
     * 如果不加 @RequestBody 会出什么问题？
     *
     * @param dto
     * @return
     */
    @Override
    @RequestMapping("/saveStorage")
    public String saveStorage(@RequestBody StorageDTO dto) {
        StorageModel sm = new StorageModel();

        sm.setUuid(dto.getUuid());
        sm.setProductId(dto.getProductId());
        sm.setNum(dto.getNum());

        service.saveStorage(sm);

        return "now save storage===" + sm;
    }
}