package com.hxkj.wechatorders.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**项目中用到的url
 * Create by wangbin
 * 2018-09-28-15:50
 */
@Data
@ConfigurationProperties(prefix = "url")
@Component
public class ProjectUrlConfig {

    //微信公众平台授权url
    public String wechatMpAuthorize;
    //微信开放平台授权url
    public String wechatOpenAuthorize;
    //点餐系统
    public String sell;

}
