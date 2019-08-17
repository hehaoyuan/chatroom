package com.hhy.java.vo;

/**
 * @Information:客户端和服务端都会用到的类，将其封装，type(行为的类型 1表示注册，表示私聊)、
 * content（发送到服务器的具体内容）、to（给谁发,私聊告知服务器要将信息发给哪个客户）
 * 服务器与客户端传递信息的载体
 * @Author: HeHaoYuan
 * @Date: Created at 0:01 on 2019/8/17
 * @Package_Name: com.hhy.java.vo
 */
public class MessageVO {
    private String type;
    private String content;
    private String to;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }
}

