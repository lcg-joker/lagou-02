package com.lcg.edu.utils;

import com.lcg.edu.annotations.Component;

import java.sql.Connection;
import java.sql.SQLException;

/**
 * 数据库连接工具类，保证每个线程是同一个数据库连接
 *
 * @author lichenggang
 * @date 2020/3/1 3:41 上午
 * @description
 */
@Component
public class ConnectionUtils {


    //保证每个线程数据库连接是同一个
    private ThreadLocal<Connection> threadLocal = new ThreadLocal<>();


    public Connection getCurrentThreadConn() throws SQLException {

        Connection conn = threadLocal.get();
        if (conn == null) {
            conn = DruidUtils.getInstance().getConnection();
            threadLocal.set(conn);
        }
        return conn;
    }

}
