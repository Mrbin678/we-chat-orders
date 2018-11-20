package com.hxkj.wechatorders.service;

import com.hxkj.wechatorders.dataobject.SellerInfo;

/** 卖家端service
 * Create by wangbin
 * 2018-09-28-14:28
 */
public interface SellerService {

    /*通过openid查询卖家端信息*/
    SellerInfo findSellerInfoByOpenid(String openid);
}
