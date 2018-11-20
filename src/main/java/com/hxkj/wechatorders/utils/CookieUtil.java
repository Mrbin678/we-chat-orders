package com.hxkj.wechatorders.utils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**cookie工具类
 * Create by wangbin
 * 2018-10-29-13:49
 */
public class CookieUtil {
    /**
     * 方法说明：设置cookie
     * @author wangbin
     * @date 2018/10/29 13:52
     * @param response
     * @param name
     * @param value
     * @param maxAge 过期时间
     * @return void
     * @throws
     */
    public static void set(HttpServletResponse response,
                           String name,
                           String value,
                           int maxAge){

        Cookie cookie = new Cookie(name,value);
        cookie.setPath("/");
        cookie.setMaxAge(maxAge);
        response.addCookie(cookie);

    }
    /**
     * 方法说明：获取cookie
     * @author wangbin
     * @date 2018/10/30 9:28
     * @param request
     * @param name
     * @return javax.servlet.http.Cookie
     * @throws
     */
    public static Cookie get(HttpServletRequest request,
                           String name){

        Map<String,Cookie> cookieMap = readCookieMap(request);
        //查看map中是否包含对应的key，有的话返回对应的值，没有则返回null
        if(cookieMap.containsKey(name)){
            return cookieMap.get(name);
        }else{
            return null;
        }
    }
    /**
     * 方法说明：将cookie封装成Map返回
     * @author wangbin
     * @date 2018/10/30 9:28
     * @param request
     * @return java.util.Map<java.lang.String,javax.servlet.http.Cookie>
     * @throws
     */
    private static Map<String,Cookie> readCookieMap(HttpServletRequest request){

        Map<String,Cookie> cookieMap = new HashMap<>();
        //从cookie中获取值，request中最早获取的是一个cookie数组，需要进行遍历转成Map类型
        Cookie[] cookies = request.getCookies();
        if(cookies != null){
            //cookie数组不为空的情况下，遍历cookie数组然后获取每个cookie的name，
            // 以及cookie设置到Map中然后返回出去
            for (Cookie cookie:cookies){
                cookieMap.put(cookie.getName(),cookie);
            }
        }
        return  cookieMap;
    }
}
