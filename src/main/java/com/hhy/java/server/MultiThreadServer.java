package com.hhy.java.server;

import com.hhy.java.util.CommUtils;
import com.hhy.java.vo.MessageVO;
import com.mysql.fabric.xmlrpc.Client;

import java.io.IOException;
import java.io.PrintStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.Map;
import java.util.Properties;
import java.util.Scanner;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * @Information:处理每个客户端连接服务器端的类
 * @Author: HeHaoYuan
 * @Date: Created at 0:05 on 2019/8/17
 * @Package_Name: com.hhy.java.server
 */
public class MultiThreadServer {
    private static final String IP;
    private static final int port;
    //缓存当前服务器所有在线的客户端信息  String是用户名
    private static Map<String,Socket> clients = new ConcurrentHashMap<>();//线程安全的HashMap

    //类加载时初始化
    static {
        Properties pros = CommUtils.loadProperties("socket.properties");
        IP = pros.getProperty("address");
        port = Integer.parseInt(pros.getProperty("port"));
    }

    //处理每个客户端连接的内部类，新建新的连接  去处理，否则accept会一直阻塞
    private static class ExecuteClient implements Runnable{
        private Socket client;
        private Scanner in;
        private PrintStream out;

        public ExecuteClient(Socket client) {
            this.client = client;
            try {
                this.in = new Scanner(client.getInputStream());
                this.out = new PrintStream(client.getOutputStream(),
                        true,"UTF-8");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }

        //服务端接受客户端发来的信息
        @Override
        public void run() {
            while (true){
                if(in.hasNext()){
                    String jsonStrFromClient = in.nextLine();
                    //反序列化为MessageVo
                    MessageVO msgFromClient = (MessageVO)
                            CommUtils.json2object(jsonStrFromClient,MessageVO.class);
                    if (msgFromClient.getType().equals("1")){
                        //新用户注册到客户端
                        String userName = msgFromClient.getContent();
                        //将当前在线所有用户的用户名发回客户端   Http的响应
                        MessageVO meg2Client = new MessageVO();
                        meg2Client.setType("1");
                        meg2Client.setContent(CommUtils.object2Json(clients.keySet()));

                        out.println(CommUtils.object2Json(meg2Client));
                        //将新上线的用户信息发回给当前已在线的所有用户,先发信息再存，否则会将自己消息发给自己
                        sendUserLogin("新上线通知:"+userName);
                        //将当前新用户注册到服务端缓存
                        clients.put(userName,client);
                        System.out.println(userName+"上线了!");
                        System.out.println("当前聊天室共有"+clients.size()+"人");
                    }
                }
            }
        }

        //向所有在线用户发送新用户上线信息
        private void sendUserLogin(String msg){
            for (Map.Entry<String,Socket> entry : clients.entrySet()){
                Socket socket = entry.getValue();//取出当前实体的每一个socket，再调用其输出流
                try {
                    PrintStream out = new PrintStream(socket.getOutputStream(),
                            true,"UTF-8");
                    out.println(msg);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

    }

    public static void main(String[] args) throws IOException {
        ServerSocket serverSocket = new ServerSocket(port);
        //创建线程池
        ExecutorService executors = Executors.newFixedThreadPool(50);
        for (int i = 0;i < 50;i++){
            System.out.println("等待客户连接...");
            Socket client = serverSocket.accept();
            System.out.println("有新的连接，端口号为"+client.getPort());
            executors.submit(new ExecuteClient(client));//交给子线程去处理，服务器再继续侦听新的连接
        }
    }
}
