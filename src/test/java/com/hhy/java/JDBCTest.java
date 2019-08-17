package com.hhy.java;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.hhy.java.util.CommUtils;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.Assert;
import org.junit.Test;

import java.sql.*;
import java.util.Properties;

/**
 * @Information:
 * @Author: HeHaoYuan
 * @Date: Created at 14:09 on 2019/8/16
 * @Package_Name: com.java.java
 */
public class JDBCTest {
    private static DruidDataSource dataSource;
    //加载配置文件，随着类加载运行，仅一次
    static {
        Properties props = CommUtils.loadProperties("datasource.properties");
        try {
            dataSource = (DruidDataSource)DruidDataSourceFactory.createDataSource(props);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //查询
    //八月 16, 2019 2:40:16 下午 com.alibaba.druid.pool.DruidDataSource info
    //信息: {dataSource-1} inited   //阿里巴巴数据源打印，-1表示数据库的一个连接
    //id为1,用户名为:java,密码为:123,简介为:帅

    @Test
    public  void  testQuery(){
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            connection = (Connection)dataSource.getPooledConnection();
            String sql = "SELECT * FROM  user WHERE  username = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            String user = "hhl";
            String pass = "123";
            //为什么用preparedstatement?防止sql注入，由于有占位符存在，不会作为关键字处理-
            ((PreparedStatement) statement).setString(1,user);
            ((PreparedStatement) statement).setString(2,pass);

            resultSet = ((PreparedStatement) statement).executeQuery();
            if (resultSet.next()){
                System.out.println("登陆成功");
            }
            else {
                System.out.println("登录失败");
            }
//            while (resultSet.next()){
//                int id = resultSet.getInt("id");
//                String userName = resultSet.getString("username");
//                String password = resultSet.getString("password");
//                String brief = resultSet.getString("brief");
//                System.out.println("id为"+id+",用户名为:"+userName+",密码为:"+password+
//                ",简介为:"+brief);
//
//            }
        }
        catch (SQLException e){

        }finally {
            closeResource(connection,statement,resultSet);
        }
    }

//插入数据
    @Test
    public void testInsert(){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection  = (Connection)dataSource.getPooledConnection();
            String password = DigestUtils.md5Hex("123");
            String sql = "INSERT INTO user(username,password,brief)"+"VALUES(?,?,?)";
            statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,"test3");
            statement.setString(2,password);
            statement.setString(3,"还是帅!");
            int rows = statement.executeUpdate();
            Assert.assertEquals(1,rows);
        } catch (SQLException e) {
            e.printStackTrace();
        }finally {
            closeResource(connection,statement);
        }

    }

    @Test
    public void testLogin(){
        String username = "hhy'";

        //String username = "java' OR 1 = 1";  sql注入
        //SELECT * FROM user WHERE username = 'java' OR 1 = 1 AND password = '1234'  sql注入

        //String username = "java'--"; --表示注释
        String password = "123";
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet  =null;
            try {
                connection = (Connection)dataSource.getPooledConnection();
                String sql = "SELECT * FROM user WHERE username = '"+username+"" +
                        " AND password = '"+password+"'";
                //System.out.println(sql);
                statement = connection.createStatement();
                resultSet = statement.executeQuery(sql);
                if (resultSet.next()){
                    System.out.println("登陆成功");
                }
                else {
                    System.out.println("登录失败");
                }
            } catch (SQLException e) {
                e.printStackTrace();
            }finally {
                closeResource(connection,statement,resultSet);
            }
        }



    public void closeResource(Connection connection, Statement statement){
        if (connection != null){
            try {
                connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        if (statement != null){
            try {
                statement.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public void closeResource(Connection connection,Statement statement,ResultSet resultSet){
        closeResource(connection,statement);
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}
