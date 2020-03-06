package com.lcg.edu.dao;

import com.lcg.edu.entity.BankAccount;

import java.sql.SQLException;

/**
 * @author lichenggang
 * @date 2020/3/1 2:01 上午
 * @description
 */
public interface BankAccountDao {
    //查询
    BankAccount query(String carNo) throws SQLException;

    //更新
    void update(BankAccount to) throws SQLException;
}
