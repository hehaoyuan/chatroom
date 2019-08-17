package com.hhy.java.client.dao;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.pool.DruidDataSourceFactory;
import com.alibaba.druid.pool.DruidPooledConnection;
import com.hhy.java.util.CommUtils;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

/**
 * @Information:封装基础dao操作，获取数据源、连接、关闭资源等
 * @Author: HeHaoYuan
 * @Date: Created at 19:11 on 2019/8/16
 * @Package_Name: com.hhy.com.java.client.dao
 */
public class BasedDao {
    private static DruidDataSource dataSource;//阿里一个数据源
    static{
        Properties properties = CommUtils.
                loadProperties("datasource.properties");
        try {
            dataSource = (DruidDataSource) DruidDataSourceFactory.createDataSource(properties);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    protected DruidPooledConnection getConnection(){
        try {
            return (DruidPooledConnection) dataSource.getPooledConnection();
        } catch (SQLException e) {
            System.out.println("数据库连接失败");
            e.printStackTrace();
        }
        return null;
    }

    protected void closeResources(Connection connection, Statement statement){
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

    //关闭数据源的重载函数
    protected void closeResources(Connection connection, Statement statement, ResultSet resultSet){
        closeResources(connection,statement);
        if (resultSet != null){
            try {
                resultSet.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

}
