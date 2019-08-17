package com.hhy.java.client.entity;

import lombok.Data;

import java.util.Set;

/**
 * @Information:
 * @Author: HeHaoYuan
 * @Date: Created at 19:34 on 2019/8/16
 * @Package_Name: com.hhy.java.client.entity
 */

@Data
public class User {
    private Integer id;//int默认值为0，Integer为null，所以数据库中默认基本类型用包装类
    private String username;
    private String password;
    private String brief;
    private Set<String> usernames;

    public Set<String> getUsernames() {
        return usernames;
    }

    public void setUsernames(Set<String> usernames) {
        this.usernames = usernames;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
