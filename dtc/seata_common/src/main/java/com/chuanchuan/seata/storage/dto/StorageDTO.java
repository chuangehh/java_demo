package com.chuanchuan.seata.storage.dto;

import javax.persistence.Id;
import javax.persistence.MappedSuperclass;

/**
 * 在Jpa里, 当我们在定义多个实体类时, 可能会遇到这几个实体类都有几个共同的属性, 这时就会出现很多重复代码.
 *   这时我们可以选择编写一个父类,将这些共同属性放到这个父类中, 并且在父类上加上@MappedSuperclass注解.
 */
@MappedSuperclass
public class StorageDTO implements java.io.Serializable{
	@Id
	private String uuid;
	private String productId;
	private float num;

	public String getUuid() {
		return uuid;
	}
	public void setUuid(String uuid) {
		this.uuid = uuid;
	}
	public String getProductId() {
		return productId;
	}
	public void setProductId(String productId) {
		this.productId = productId;
	}
	public float getNum() {
		return num;
	}
	public void setNum(float num) {
		this.num = num;
	}

	@Override
	public String toString() {
		return "StorageDTO [uuid=" + uuid + ", productId=" + productId + ", num=" + num + "]";
	}
}
