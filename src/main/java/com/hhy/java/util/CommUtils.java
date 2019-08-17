package com.hhy.java.util;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * @Information:封装公共工具方法，如加载配置文件、json序列化
 * @Author: HeHaoYuan
 * @Date: Created at 13:48 on 2019/8/16
 * @Package_Name: com.java.util
 */
public class CommUtils {
    //成熟的第三方开源库,gson在谷歌库上
    private static final Gson GSON = new GsonBuilder().create();
    /**
    * @Author: HeHaoYuan
    * @Date: 2019/8/16
    * @Description:
    加载配置文件名称,fileName是其名称
     先获取一个文件或者网络输入流 用最顶层的InputStream接受

    */
    //ctrl+shift+T 单元测试，由开发人员写
    public static Properties loadProperties(String fileName){
        Properties properties = new Properties();
        //获取类加载器，加载指定文件变为输入流（）只要资源文件和类在一个路径下，就可以加载它变为输入流
        InputStream in = CommUtils.class.getClassLoader().getResourceAsStream(fileName);//核心代码
        try{
            //加载资源
            properties.load(in);
        } catch (IOException e) {
            return null;
        }
        return properties;
    }

    /**
    * @Author: HeHaoYuan
    * @Date: 2019/8/16
    * @Description:
    将任意字符串变为json字符串

    */
    public static String object2Json(Object obj){
        return GSON.toJson(obj);
    }

    /**
    * @Author: HeHaoYuan
    * @Date: \
    * @Description:反序列化(反射原理) jsonStr json字符串 objClass 反序列化的类反射对象
    */
    public static Object json2object(String jsonStr,Class objClass){
        return GSON.fromJson(jsonStr,objClass);
    }
}
