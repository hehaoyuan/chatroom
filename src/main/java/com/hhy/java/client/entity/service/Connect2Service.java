package com.hhy.java.client.entity.service;
import com.hhy.java.util.CommUtils;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.Socket;
import java.util.Properties;

/**
 * @Information:当用户点击登录时，客户端进行页面跳转，连接还在，页面不能直接的关闭  客户端连接的输入输出流
 * @Author: HeHaoYuan
 * @Date: Created at 12:39 on 2019/8/17
 * @Package_Name: com.hhy.java.client.entity.service
 */
public class Connect2Service {
    private static final String IP;
    private static final int port;
    static {
        Properties pros = CommUtils.loadProperties("socket.properties");
        IP = pros.getProperty("address");
        port = Integer.parseInt(pros.getProperty("port"));
    }



    private Socket client;
    private InputStream in;
    private OutputStream out;


    public Connect2Service() {
        try {
            client = new Socket(IP,port);
            //建立连接成功的时候，输入输出流可以得到
            in = client.getInputStream();
            out = client.getOutputStream();
        } catch (IOException e) {
            System.out.println("与服务器建立连接失败");
            e.printStackTrace();
        }
    }
    //获取方法 拿输入流获取服务器发来的信息
    public InputStream getIn(){
        return in;
    }
    //拿输出流给服务器发信息
    public OutputStream getOut(){
        return out;
    }
}
