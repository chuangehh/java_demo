package com.chuanchuan.seata.storage.vo;

import com.chuanchuan.seata.storage.dto.StorageDTO;

import javax.persistence.Entity;
import javax.persistence.Table;


@Entity
@Table(name = "tbl_storage")
public class StorageModel extends StorageDTO {

}
