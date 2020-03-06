package com.lcg.edu.utils;

import com.alibaba.druid.pool.DruidDataSource;

/**
 * @author lichenggang
 * @date 2020/3/1 2:26 上午
 * @description
 */
public class DruidUtils {


    //单例模式
    private DruidUtils() {
    }

    //数据库连接池对象
    private static DruidDataSource druidDataSource = new DruidDataSource();

    public static DruidDataSource getInstance() {
        return druidDataSource;
    }

    static {
        druidDataSource.setDriverClassName("com.mysql.jdbc.Driver");
        druidDataSource.setUrl("jdbc:mysql://localhost:3306/mybatis");
        druidDataSource.setUsername("root");
        druidDataSource.setPassword("root");

    }


}
