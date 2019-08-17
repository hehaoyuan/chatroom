package com.hhy;

import com.hhy.java.client.entity.User;
import com.hhy.java.util.CommUtils;
import org.junit.Assert;
import org.junit.Test;

import java.util.HashSet;
import java.util.Properties;
import java.util.Set;

/**
 * @Information:
 * @Author: HeHaoYuan
 * @Date: Created at 13:59 on 2019/8/16
 * @Package_Name: com.java.util
 */
public class CommUtilsTest {

    @Test
    public void loadProperties() {
        String fileName = "datasource.properties";
        Properties properties = CommUtils.loadProperties(fileName);
       // System.out.println(properties);
        Assert.assertNotNull(properties);//我认为他不为空 显示结果如果为绿色的√说明测试正确
        //Assert
    }

    //序列化都是深拷贝
    //{"id":1,"password":"999","brief":"帅","usernames":["test4","test2","test3"]}
    @Test
    public void object2Json(){
        User user = new User();
        user.setId(1);
        user.setPassword("999");
        user.setBrief("帅");
        Set<String> strings = new HashSet<>();
        strings.add("test2");
        strings.add("test3");
        strings.add("test4");
        user.setUsernames(strings);
        String str = CommUtils.object2Json(user);
        System.out.println(str);
    }

    //反序列化是浅拷贝

    @Test
    public void json2object(){
        String jsonStr = "{\"id\":1,\"password\":\"999\",\"brief\":\"帅\"," +
                "\"usernames\":[\"test4\",\"test2\",\"test3\"]}";
        User user = (User) CommUtils.json2object(jsonStr,User.class);
        System.out.println(user.getUsernames());
    }

}