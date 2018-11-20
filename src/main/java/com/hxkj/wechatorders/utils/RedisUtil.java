package com.hxkj.wechatorders.utils;


//import com.lottery.object.Session;
import lombok.extern.slf4j.Slf4j;
//import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.BoundHashOperations;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.Serializable;
import java.util.Set;
import java.util.concurrent.TimeUnit;


/**
 * @author: bolin
 * @description:  redis 工具类 使用时注意引入redis配置
 * @date: 10:22 2018/10/9
 * @version :   1.0.0
 */
@Slf4j
@Component
public class RedisUtil {
    @Autowired(required = false)
    private RedisTemplate redisTemplate;

    /**
     * KEY 前缀
     */
    private static final String PREFIX = "member";

    private static final String TOKEN_KEY = PREFIX + "." + "code";

    /**
     * 增加token过期时间 - 每次增加30分钟
     * @param token
     * @return
     */
    public void increaseExpireTime(String token){
        BoundHashOperations<Serializable, String, String> hashOperations = redisTemplate.boundHashOps(token);
        hashOperations.expire(30,TimeUnit.MINUTES);
    }

    /**
     * 增加token过期时间，单位秒
     * @param token
     * @param time
     */
    public void increaseExpireTime(String token,Long time){
        BoundHashOperations<Serializable, String, String> hashOperations = redisTemplate.boundHashOps(token);
        hashOperations.expire(time,TimeUnit.SECONDS);
    }

    /**
     * 批量删除对应的value
     *
     * @param keys
     */
    public void remove(final String... keys) {
        for (String key : keys) {
            remove(key);
        }
    }

    /**
     * 批量删除key
     *
     * @param pattern
     */
    public void removePattern(final String pattern) {
        Set<Serializable> keys = redisTemplate.keys(pattern);
        if (keys.size() > 0) {
            redisTemplate.delete(keys);
        }
    }

    /**
     * 删除对应的value
     *
     * @param key
     */
    public void remove(final String key) {
        if (exists(key)) {
            redisTemplate.delete(key);
        }
    }

    /**
     * 判断缓存中是否有对应的value
     *
     * @param key
     * @return
     */
    public boolean exists(final String key) {
        return redisTemplate.hasKey(key);
    }

    /**
     * 读取缓存
     *
     * @param key
     * @return
     */
    public Object get(final String key) {
        Object result = null;
        ValueOperations<Serializable, Object> operations = redisTemplate
                .opsForValue();
        result = operations.get(key);
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @return
     */
    public boolean set(final String key, Object value) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            result = true;
        } catch (Exception e) {
            log.error("异常：",e);
        }
        return result;
    }

    /**
     * 写入缓存
     *
     * @param key
     * @param value
     * @param expireTime 单位 秒
     * @return
     */
    public boolean set(final String key, Object value, Long expireTime) {
        boolean result = false;
        try {
            ValueOperations<Serializable, Object> operations = redisTemplate
                    .opsForValue();
            operations.set(key, value);
            redisTemplate.expire(key, expireTime, TimeUnit.SECONDS);
            result = true;
        } catch (Exception e) {
            log.error("异常：",e);
        }
        return result;
    }

    /**
     * 保存Session信息
     * @param session
     */
//    public Boolean saveSession(Session session) {
//       boolean result = false;
//         try{
//            BoundHashOperations<Serializable, String, Session> hashOperations = redisTemplate.boundHashOps(TOKEN_KEY + "." + session.getToken());
//            hashOperations.put("token", session);
//            hashOperations.expire(30, TimeUnit.MINUTES);
//            result = true;
//        } catch (Exception e) {
//            log.error("异常：",e);
//        }
//        return result;
//    }

    /**
     * 根据token获取Session信息
     * @param token 从登录接口获取的token
     * @return Session
     */
//    public Session getSession(String token) {
//        if (StringUtils.isEmpty(token)) {
//            return null;
//        }
//        if (!exists(token)) {
//            return null;
//        }
//        BoundHashOperations<Serializable, String, Session> hashOperations = redisTemplate.boundHashOps(TOKEN_KEY + "." + token);
//        return hashOperations.get(token);
//    }
}
