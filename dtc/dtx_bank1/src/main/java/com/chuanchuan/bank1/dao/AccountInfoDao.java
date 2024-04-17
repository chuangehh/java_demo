package com.chuanchuan.bank1.dao;

import org.apache.ibatis.annotations.*;
import org.springframework.stereotype.Component;

@Mapper
@Component
public interface AccountInfoDao {

    @Select("SELECT count(1) FROM de_duplication WHERE tx_no = #{txNo}")
    int existsTxNo(@Param("txNo") String txNo);

    @Insert("INSERT INTO de_duplication(tx_no, create_time) VALUES(#{txNo}, now())")
    void insertTxNo(String txNo);

    @Update("UPDATE account_info SET account_balance = account_balance + #{amount} WHERE account_no = #{accountNo}")
    void updateUserAccount(@Param("accountNo") String accountNo, @Param("amount") double amount);
}