package com.hhy.java.client.entity.service;

import com.hhy.java.client.dao.AccountDao;
import com.hhy.java.client.entity.User;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

/**
 * @Information:
 * @Author: HeHaoYuan
 * @Date: Created at 21:28 on 2019/8/16
 * @Package_Name: com.hhy.java.client.entity.service
 */
public class userReg {

    private JTextField account;
    private JPasswordField pass;
    private JTextField myself;
    private JLabel username;
    private JLabel password;
    private JLabel introduce;
    private JPanel first;
    private JPanel second;
    private JPanel third;
    private JPanel whole;
    private JButton 注册按钮;
    //关于数据库增删改查用户的类
    private AccountDao accountDao = new AccountDao();

    public userReg() {
        JFrame frame = new JFrame("用户注册");
        frame.setContentPane(first);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);

        //点击注册按钮，将信息持久化到db中，成功弹出提示框

        注册按钮.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //弹出提示框

                //正确的提示框
                //JOptionPane.showMessageDialog(frame,"注册成功!","提示信息",JOptionPane.INFORMATION_MESSAGE);

                String username = account.getText();
                String password = String.valueOf(pass.getPassword());
                String brief = myself.getText();
                //将输入信息包装为user类，保存到数据库中
                User user = new User();
                user.setUsername(username);
                user.setPassword(password);
                user.setBrief(brief);
                //调用dao对象
                if (accountDao.userReg(user)){
                    //成功后返回登录页面
                    JOptionPane.showMessageDialog(frame,"注册成功!","成功信息",JOptionPane.INFORMATION_MESSAGE);
                    frame.setVisible(false);//将当前注册页面不可见的方法
                }
                else {
                    //弹出提示框
                    //仍保留当前注册页面
                    JOptionPane.showMessageDialog(frame,"注册失败!","错误信息",JOptionPane.ERROR_MESSAGE);
                }

            }
        });
    }
}
