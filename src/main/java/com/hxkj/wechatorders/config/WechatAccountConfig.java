package com.hxkj.wechatorders.config;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * 微信账号相关配置
 * Created by wangbin
 * 2018-08-09 14:23
 */
@Data
@Component
@ConfigurationProperties(prefix = "wechat")
public class WechatAccountConfig {
    /*开放平台appid*/
    private String openAppId;
    /*开放平台密钥*/
    private String openAppSecret;

    /**
     * 公众号appId
     */
    private String mpAppId;
    /**
     * 公众号appSecret密钥
     */
    private String mpAppSecret;
    /**
     * 商户号
     */
    private String mchId;
    /**
     * 商户密钥
     */
    private String mchKey;
    /**
     * 商户证书路径
     */
    private String keyPath;
    /**
     * 微信支付异步通知地址
     */
    private String notifyUrl;
    /**
     * 微信模板id
     */
    private Map<String,String> templateId;

}
