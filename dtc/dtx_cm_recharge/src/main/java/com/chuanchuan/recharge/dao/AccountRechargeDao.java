package com.chuanchuan.recharge.dao;

import com.chuanchuan.recharge.entity.AccountRecharge;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AccountRechargeDao {
    @Insert("insert into account_recharge(id,account_no,recharge_amount,result) values(#{id},#{accountNo},#{rechargeAmount},#{result})")
    int insertAccountRecharge(@Param("id") String id, @Param("accountNo") String accountNo, @Param("rechargeAmount") Double recharge_amount, @Param("result") String result);

    @Select("select * from account_recharge where id=#{txNo}")
    AccountRecharge findByIdTxNo(@Param("txNo") String txNo);
}
