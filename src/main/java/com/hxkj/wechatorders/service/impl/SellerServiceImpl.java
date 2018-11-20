package com.hxkj.wechatorders.service.impl;

import com.hxkj.wechatorders.dataobject.SellerInfo;
import com.hxkj.wechatorders.repository.SellerInfoRepository;
import com.hxkj.wechatorders.service.SellerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**卖家端service实现类
 * Create by wangbin
 * 2018-09-28-14:32
 */
@Service
public class SellerServiceImpl implements SellerService {

    @Autowired
    private SellerInfoRepository repository;

    @Override
    public SellerInfo findSellerInfoByOpenid(String openid) {
        return repository.findByOpenid(openid);
    }
}
