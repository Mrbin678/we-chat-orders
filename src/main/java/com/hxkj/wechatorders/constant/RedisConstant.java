package com.hxkj.wechatorders.constant;

/**redis常量
 * Create by wangbin
 * 2018-10-29-14:00
 */
public interface RedisConstant {

    String TOKEN_PREFIX = "toekn_%s";//设置token存储的key是以token_为开头的
    Integer EXPIRE = 7200;//设置过期时间为2小时


}
