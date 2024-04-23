package com.chuanchuan.seata.storage.api;

import com.chuanchuan.seata.storage.dto.StorageDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;


@FeignClient(value="seataStorageService")
public interface StorageWebApi {
	@RequestMapping("/saveStorage")
	public String saveStorage(@RequestBody StorageDTO dto);
}


