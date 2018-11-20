package com.hxkj.wechatorders.config;

import me.chanjar.weixin.mp.api.WxMpConfigStorage;
import me.chanjar.weixin.mp.api.WxMpInMemoryConfigStorage;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.api.impl.WxMpServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**微信公众号的配置，在微信中Mp代表公众号
 * Created by wangbin
 * 2018-08-09 14:00
 */
@Component
public class WechatMpConfig {

    @Autowired
    private WechatAccountConfig accountConfig;

    /**
     * @Bean 注解放在方法上指明这个方法产生的bean交给spring容器管理
     * Spring的@Bean注解用于告诉方法，产生一个Bean对象，
     * 然后这个Bean对象交给Spring管理。产生这个Bean对象的方法Spring只会调用一次，
     * 随后这个Spring将会将这个Bean对象放在自己的IOC容器中
     * @return
     */
    @Bean
    public WxMpService wxMpService(){
        WxMpService wxMpService = new WxMpServiceImpl();
        wxMpService.setWxMpConfigStorage(wxMpConfigStorage());
        return wxMpService;
    }

    @Bean
    public WxMpConfigStorage wxMpConfigStorage(){
        WxMpInMemoryConfigStorage wxMpConfigStorage = new WxMpInMemoryConfigStorage();
        wxMpConfigStorage.setAppId(accountConfig.getMpAppId());
        wxMpConfigStorage.setSecret(accountConfig.getMpAppSecret());
       return wxMpConfigStorage;
    }
}
