package com.hxkj.wechatorders.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**微信开放平台配置
 * Create by wangbin
 * 2018-09-28-15:01
 */
@Component
public class WechatOpenConfig {

    @Autowired
    private WechatAccountConfig accountConfig;

    @Bean//这里用的第三方sdk,加载配置信息，返回给微信服务（有了微信服务bean了）
    public WxMpService wxOpenService(){
        WxMpService wxOpenService = new WxMpServiceImpl();
        wxOpenService.setWxMpConfigStorage(wxOpenConfigStorage());
        return wxOpenService;
    }

    @Bean//这里用的第三方sdk,读取配置信息，设置开放平台的appid和appsecret
    public WxMpConfigStorage wxOpenConfigStorage(){
        WxMpInMemoryConfigStorage  wxMpInMemoryConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpInMemoryConfigStorage.setAppId(accountConfig.getOpenAppId());
        wxMpInMemoryConfigStorage.setSecret(accountConfig.getOpenAppSecret());
        return  wxMpInMemoryConfigStorage;
    }

}
