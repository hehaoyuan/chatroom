package com.hhy.java.client.dao;

import com.hhy.java.client.entity.User;
import org.apache.commons.codec.digest.DigestUtils;

import java.sql.*;

/**
 * @Information:
 * @Author: HeHaoYuan
 * @Date: Created at 19:26 on 2019/8/16
 * @Package_Name: com.hhy.com.java.client.dao
 */
public class AccountDao extends BasedDao{
    //用户注册需要那些对象 insert

//是否注册成功
    public boolean userReg(User user){
        Connection connection = null;
        PreparedStatement statement = null;
        try {
            connection = getConnection();
            //预编译
            String sql = "INSERT INTO user(username,password,brief)"+"VALUES(?,?,?)";//需要外界传递
            statement = connection.prepareStatement(sql,Statement.RETURN_GENERATED_KEYS);
            statement.setString(1,user.getUsername());
            statement.setString(2,DigestUtils.md5Hex(user.getPassword()));
            statement.setString(3,user.getBrief());
            int rows = statement.executeUpdate();
            if (rows == 1){
                return true;
            }
        }
        catch (SQLException e){
            System.err.println("用户注册失败");
            e.printStackTrace();
        }finally {
            closeResources(connection,statement);
        }
        return false;
    }


    public User userlogin(String username,String password){
        Connection connection = null;//两个业务不能用同一个connection，两个事物
        PreparedStatement statement = null;
        ResultSet resultSet = null;
        try {
            connection = getConnection();
            String sql = "SELECT * FROM user WHERE username = ? AND password = ?";
            statement = connection.prepareStatement(sql);
            statement.setString(1,username);
            statement.setString(2,DigestUtils.md5Hex(password));
            resultSet = statement.executeQuery();
            if (resultSet.next()){
                User user = getuser(resultSet);
                return user;
            }
        }catch (SQLException e){
            System.out.println("用户登录失败");
            e.printStackTrace();

        }finally {
            closeResources(connection,statement,resultSet);
        }
        return null;
    }

    private User getuser(ResultSet resultSet) throws SQLException {
        User user = new User();
        user.setId(resultSet.getInt("id"));
        user.setUsername(resultSet.getString("username"));
        user.setPassword(resultSet.getString("password"));
        user.setBrief(resultSet.getString("brief"));
        return user;
    }
}
