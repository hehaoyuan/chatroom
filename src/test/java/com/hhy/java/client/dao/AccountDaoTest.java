package com.hhy.java.client.dao;

import com.hhy.java.client.entity.User;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.*;

/**
 * @Information:
 * @Author: HeHaoYuan
 * @Date: Created at 20:10 on 2019/8/16
 * @Package_Name: com.hhy.java.client.dao
 */
public class AccountDaoTest {
    private AccountDao accountDao = new AccountDao();

    @Test
    public void userReg() {
        //注册
        User user = new User();
        user.setUsername("aaa");
        user.setPassword("456");
        user.setBrief("帅还是帅");
        boolean flag = accountDao.userReg(user);
        Assert.assertTrue(flag);
    }

    @Test
    public void userlogin() {
        //查询注册
        String username = "aaa";
        String password = "456";
        User user = accountDao.userlogin(username,password);
        System.out.println(user);
        Assert.assertNotNull(user);
    }
}