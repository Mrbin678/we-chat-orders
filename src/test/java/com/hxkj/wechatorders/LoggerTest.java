package com.hxkj.wechatorders;

import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

/**
 * Created by wangbin
 * 2018-07-09 10:38
 * 使用了@Slf4j 这个注解就不用在每个类中创建logger对象，传入各个类的类名来记录日志了,
 * 有可能出现log没反应的情况，这需要我们下载lombok插件重启idea
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class LoggerTest {

//    private final Logger logger = LoggerFactory.getLogger(LoggerTest.class);

    @Test
    public void test1(){
//        logger.debug("debug....");
//        logger.info("info....");
//        logger.error("error...");
        String name = "com.hxkj";
        String password = "123456";
        log.debug("debug...");
        log.info("info...");
        //输出变量的两种方式，推荐第二种直接填变量的方式，不用拼接字符串
        log.info("name:"+name+"password:"+password);
        log.info("name:{},password:{}",name,password);
        log.error("error...");

    }

}
