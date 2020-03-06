package com.lcg.edu.dao.impl;

import com.lcg.edu.annotations.Autowired;
import com.lcg.edu.annotations.Repository;
import com.lcg.edu.dao.BankAccountDao;
import com.lcg.edu.entity.BankAccount;
import com.lcg.edu.utils.ConnectionUtils;
import com.lcg.edu.utils.DruidUtils;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

/**
 * @author lichenggang
 * @date 2020/3/1 2:02 上午
 * @description
 */
@Repository("bankAccountDao")
public class BankAccountDaoImpl implements BankAccountDao {


    @Autowired
    private ConnectionUtils connectionUtils;

    @Override
    public BankAccount query(String carNo) throws SQLException {


        //获取一个数据库连接
        Connection connection = connectionUtils.getCurrentThreadConn();


        String sql = "select * from account where cardNo=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setString(1, carNo);

        //查询
        ResultSet resultSet = preparedStatement.executeQuery();
        BankAccount account = new BankAccount();
        while (resultSet.next()) {
            account.setCardNo(resultSet.getString("cardNo"));
            account.setName(resultSet.getString("name"));
            account.setMoney(resultSet.getInt("money"));
        }
        //关闭连接
        resultSet.close();
        preparedStatement.close();
//        connection.close();

        return account;
    }

    @Override
    public void update(BankAccount bankAccount) throws SQLException {
        //获取一个数据库连接
        Connection connection = connectionUtils.getCurrentThreadConn();

        String sql = "update account set money=? where cardNo=?";
        PreparedStatement preparedStatement = connection.prepareStatement(sql);
        preparedStatement.setInt(1, bankAccount.getMoney());
        preparedStatement.setString(2, bankAccount.getCardNo());

        //更新
        int i = preparedStatement.executeUpdate();

        //关闭连接
        preparedStatement.close();
//        connection.close();


    }
}
