package com.hxkj.wechatorders.utils;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

/**自定义Json格式化工具类
 * Created by wangbin
 * 2018-08-13 13:49
 */
public class JsonUtil {
    /**
     * 将object对象装换成json
     * @param object
     * @return
     */
    public static String toJson(Object object){
        GsonBuilder gsonBuilder = new GsonBuilder();
        gsonBuilder.setPrettyPrinting();
        Gson gson = gsonBuilder.create();
        return gson.toJson(object);
    }


}
