package com.hxkj.wechatorders;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication //启动类注解
@MapperScan(basePackages = "com.hxkj.wechatorders.dataobject.mapper")//使用这个注解扫描mapper接口
@EnableCaching //在启动类上加上这个注解，启用缓存
public class WeChatOrdersApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeChatOrdersApplication.class, args);
	}

}
