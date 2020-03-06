package com.lcg.edu.utils;

import com.lcg.edu.annotations.Autowired;
import com.lcg.edu.annotations.Component;

import java.sql.SQLException;

/**
 * 事务管理器类：负责手动事务的开启、提交、回滚
 *
 * @author lichenggang
 * @date 2020/3/1 3:48 上午
 * @description
 */
@Component
public class TransactionManager {

    @Autowired
    private ConnectionUtils connectionUtils;


    // 开启手动事务控制
    public void beginTransaction() throws SQLException {
        connectionUtils.getCurrentThreadConn().setAutoCommit(false);
    }


    // 提交事务
    public void commit() throws SQLException {
        connectionUtils.getCurrentThreadConn().commit();
    }


    // 回滚事务
    public void rollback() throws SQLException {
        connectionUtils.getCurrentThreadConn().rollback();
    }


}
