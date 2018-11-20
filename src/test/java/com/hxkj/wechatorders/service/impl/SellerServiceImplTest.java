package com.hxkj.wechatorders.service.impl;

import com.hxkj.wechatorders.dataobject.SellerInfo;
import com.hxkj.wechatorders.service.SellerService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Create by wangbin
 * 2018-09-28-14:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class SellerServiceImplTest {

    private static final String openid = "abc";
    @Autowired
    private SellerService sellerServiceImpl;

    @Test
    public void findSellerInfoByOpenid() throws Exception {
        SellerInfo result = sellerServiceImpl.findSellerInfoByOpenid(openid);
        Assert.assertEquals(openid,result.getOpenid());
    }
}