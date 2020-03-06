package com.lcg.edu.entity;

/**
 * @author lichenggang
 * @date 2020/3/1 1:57 上午
 * @description
 */
public class BankAccount {

    //卡号
    private String cardNo;
    //姓名
    private String name;
    //余额
    private int money;


    public String getCardNo() {
        return cardNo;
    }

    public void setCardNo(String cardNo) {
        this.cardNo = cardNo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }
}
