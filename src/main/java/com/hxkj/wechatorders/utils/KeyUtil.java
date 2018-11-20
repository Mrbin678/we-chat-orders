package com.hxkj.wechatorders.utils;

import java.util.Random;

/**帮我们自动生成key的工具类
 * Created by wangbin
 * 2018-07-12 14:41
 */
public class KeyUtil {
    /**
     * 生成唯一的主键
     * 格式：时间+随机数
     * synchronized关键字，防止生成的随机数重复（多线程的知识）
     * @return
     */
    public static synchronized String genUniqueKey(){
        //随机数
        Random random = new Random();
        //系统当前的毫秒数
         System.currentTimeMillis();
        //生成6位随机数，random的nextInt(int n)方法可以生成一个介于0(包含)到n(不包含)之间的整数
        //random.nextInt(90000) 生成0-899999 包含0的随机整数，
        // 加上100000就是产生100001-999999的6位随机数了
         Integer number = random.nextInt(900000)+100000;
         return  System.currentTimeMillis() + String.valueOf(number);
    }


}
