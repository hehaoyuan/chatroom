package com.hhy.java.client.entity.service;

import javax.swing.*;
import java.util.Iterator;
import java.util.Set;

/**
 * @Information:
 * @Author: HeHaoYuan
 * @Date: Created at 15:35 on 2019/8/17
 * @Package_Name: com.hhy.java.client.entity.service
 */
public class FriendsList {
    private JPanel friendsPanel;
    private JScrollPane friendsList;
    private JButton creategroupButton;
    private JLabel groupLabel;
    private JScrollPane groupPanel;
    private JLabel friendLabel;
    private  JFrame frame;

    private String username;
    private Set<String> users;
    private Connect2Service connect2Service;


    //从登录页面跳到列表页面需要传参
   public FriendsList(String username,Set<String>users,
                      Connect2Service connect2Service){
       this.username = username;
       this.users = users;
       this.connect2Service = connect2Service;

       frame = new JFrame(username);
       frame.setContentPane(friendsPanel);
       frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
       frame.setSize(400,300);
       frame.setLocationRelativeTo(null);
       frame.pack();
       frame.setVisible(true);
       loadUsers();
   }

   //加载所有在线用户信息
    public void loadUsers(){
       JLabel[] userLabels = new JLabel[users.size()];
       JPanel friends = new JPanel();
       friends.setLayout(new BoxLayout(friends,BoxLayout.Y_AXIS));
       //迭代users内容展示
        Iterator<String> iterator = users.iterator();
        int i = 0;
        while (iterator.hasNext()){
            String userName = iterator.next();
            userLabels[i] = new JLabel(userName);
            friends.add(userLabels[i]);
            i++;
        }
        friendsList.setViewportView(friends);
        //设置滚动条垂直滚动
        friendsList.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        friends.revalidate();
        friendsList.revalidate();

    }
}
