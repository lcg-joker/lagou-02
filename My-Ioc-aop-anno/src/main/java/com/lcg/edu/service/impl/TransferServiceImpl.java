package com.lcg.edu.service.impl;

import com.lcg.edu.annotations.Autowired;
import com.lcg.edu.annotations.Service;
import com.lcg.edu.annotations.Transactional;
import com.lcg.edu.dao.BankAccountDao;
import com.lcg.edu.entity.BankAccount;
import com.lcg.edu.service.TransferService;

import java.sql.SQLException;

/**
 * @author lichenggang
 * @date 2020/3/1 2:12 上午
 * @description
 */
@Service("transferService")

public class TransferServiceImpl implements TransferService {

    @Autowired
    private BankAccountDao bankAccountDao;


    @Override
    @Transactional
    public void transfer(String fromCardNo, String toCardNo, int money) throws SQLException {

        try {

            BankAccount from = bankAccountDao.query(fromCardNo);
            BankAccount to = bankAccountDao.query(toCardNo);

            from.setMoney(from.getMoney() - money);
            to.setMoney(to.getMoney() + money);

            bankAccountDao.update(to);
            bankAccountDao.update(from);

        } catch (Exception e) {
            e.printStackTrace();
            throw e;
        }


    }


}
