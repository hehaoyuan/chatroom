package com.hhy.java.util;

import com.hhy.java.client.dao.AccountDao;
import com.hhy.java.client.entity.User;
import com.hhy.java.client.entity.service.Connect2Service;
import com.hhy.java.client.entity.service.FriendsList;
import com.hhy.java.client.entity.service.userReg;
import com.hhy.java.vo.MessageVO;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.PrintStream;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;
import java.util.Set;

/**
 * @Information:
 * @Author: HeHaoYuan
 * @Date: Created at 17:34 on 2019/8/16
 * @Package_Name: com.java.util
 */
public class TestGUI {
    private JPanel GUITestPanel;
    private JPanel qqPanel;
    private JPasswordField passwordField;
    private JTextField nameField;
    private JLabel username;
    private JLabel password;
    private JLabel My_chatroom;
    private JButton register;
    private JButton login;
    private JFrame frame;

    //单例模式
    private AccountDao accountDao = new AccountDao();


    //默认创建无参构造
    public TestGUI() {
        frame = new JFrame("用户登录");
        //最外层盘子 ,防止无限递归
        frame.setContentPane(GUITestPanel);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setLocationRelativeTo(null);
        frame.pack();
        frame.setVisible(true);


        //注册按钮
        register.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //弹出注册页面
                new userReg();
            }
        });


        //登录按钮
        login.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                //登录页面

                //校验用户信息
                String username =nameField.getText();
                String password = String.valueOf(passwordField.getPassword());
                //查询数据库,根据user返回值是否为null
                User user = accountDao.userlogin(username,password);
                if (user != null){
                    //成功 ，加载用户列表
                    JOptionPane.showMessageDialog(frame,"登录成功!","成功信息",
                            JOptionPane.INFORMATION_MESSAGE);
                    frame.setVisible(false);
                    //与服务器建立连接，将当前用户的用户名与密码发到服务端
                    Connect2Service connect2Service = new Connect2Service();
                    MessageVO msg2Server = new MessageVO();
                    msg2Server.setType("1");//注册
                    msg2Server.setContent(username);
                    //把要发的字符串序列化为json对象
                    String json2Server = CommUtils.object2Json(msg2Server);
                    //要发送信息就要获取连接的输出流  autoFlush自动刷新缓存区，编码为utf-8
                    try {
                        PrintStream out = new PrintStream(connect2Service.getOut(),
                                true,"UTF-8");
                        out.println(json2Server);//将当前用户名信息发给服务端
                        //读取服务端发回的所有在线用户信息
                        Scanner in = new Scanner(connect2Service.getIn());
                        if (in.hasNext()){
                            String msgFromServerstr = in.nextLine();
                            MessageVO msgFromServer = (MessageVO) CommUtils.json2object(
                                    msgFromServerstr,MessageVO.class);
                            Set<String> users = (Set<String>) CommUtils.
                                    json2object(msgFromServer.getContent(),Set.class);
                            System.out.println("所有在线用户为:"+users);
                            //加载用户列表界面
                            //将当前用户名、所有在线好友、与服务器建立连接传递到好友列表界面
                            new FriendsList(username,users,connect2Service);
                        }
                    } catch (UnsupportedEncodingException e1) {
                        e1.printStackTrace();
                    }
                }
                else {
                    //失败，停留当前登录页面，提示用户信息错误
                    JOptionPane.showMessageDialog(frame,"登录失败!","错误信息",
                            JOptionPane.ERROR_MESSAGE);
                }
            }
        });
    }



    public static void main(String[] args) {
        new TestGUI();
    }
}
