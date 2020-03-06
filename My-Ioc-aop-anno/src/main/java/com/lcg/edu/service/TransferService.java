package com.lcg.edu.service;

import java.sql.SQLException;

/**
 * @author lichenggang
 * @date 2020/3/1 2:12 上午
 * @description
 */

public interface TransferService {


    //转账
    void transfer(String fromCardNo, String toCardNo, int money) throws SQLException;
}
