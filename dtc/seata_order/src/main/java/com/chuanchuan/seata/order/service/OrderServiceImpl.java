package com.chuanchuan.seata.order.service;

import com.chuanchuan.seata.order.dao.OrderDAO;
import com.chuanchuan.seata.order.vo.OrderModel;
import io.seata.spring.annotation.GlobalTransactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;


@Service
@Transactional
public class OrderServiceImpl implements OrderService {

    @Autowired
    private OrderDAO dao;

    /**
     * 1 TM向TC发起全局事务，注册成功后生产XID
     * 2 TC(Seata服务器)通知RM(本订单模块)去执行本地事务
     * 2.1 处理本地业务逻辑
     * 2.2 写业务表
     *
     * @param sm
     * @return
     */
    @Override
    @GlobalTransactional
    public String saveOrder(OrderModel sm) {
        //1 先处理order的数据
        dao.saveOrder(sm); //RM01

        //2 调用库存，RM02
        return "ok---->" + sm;
    }

}





/*@Service
@Transactional
public class OrderServiceImpl implements OrderService{
	@Autowired
	private OrderDAO dao;

	@Autowired
	private StorageWebApi api;

	@GlobalTransactional
	public String saveOrder(OrderModel sm) //TM
	{
		//1 先处理order的数据
		dao.saveOrder(sm); //RM

		//2 再调用仓储服务，去处理库存的数据
		StorageDTO dto = new StorageDTO();
		dto.setUuid("ss-"+sm.getUuid());
		dto.setProductId("productId-"+sm.getUuid());
		dto.setNum(new Random().nextInt(5)+1);
		
		api.saveStorage(dto);

		//3 回滚测试判断
		if(sm.getTotalMoney() == 4444) {
			int a = 5/0;
		}
		return "ok---->"+sm;
	}

}*/
