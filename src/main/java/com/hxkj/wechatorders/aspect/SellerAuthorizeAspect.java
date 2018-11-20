package com.hxkj.wechatorders.aspect;



import com.hxkj.wechatorders.constant.CookieConstant;
import com.hxkj.wechatorders.constant.RedisConstant;
import com.hxkj.wechatorders.exception.SellerAuthorizeException;
import com.hxkj.wechatorders.utils.CookieUtil;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

/** 卖家端切面授权操作
 * Create by wangbin
 * 2018-10-30-15:17
 */
@Aspect
@Component
@Slf4j
public class SellerAuthorizeAspect {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Pointcut("execution(public * com.hxkj.wechatorders.controller.Seller*.*(..))"+
    "&&!execution(public * com.hxkj.wechatorders.controller.SellerUserController.*(..))")
    public void verify(){}

    @Before("verify()")
    public void doVerify(){
        //从http请求中获取request
        ServletRequestAttributes attributes = (ServletRequestAttributes)RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();
        //查询cookie
        Cookie cookie = CookieUtil.get(request, CookieConstant.TOKEN);
        if(cookie == null){
            log.warn("[登陆校验] Cookie中查不到token");
            throw new SellerAuthorizeException();
        }
        //去redis中查询
        String tokenValue = redisTemplate.opsForValue().get(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
        if(StringUtils.isEmpty(tokenValue)){
            log.warn("[登陆校验] Redis中查不到token");
            throw new SellerAuthorizeException();
        }
    }




}
