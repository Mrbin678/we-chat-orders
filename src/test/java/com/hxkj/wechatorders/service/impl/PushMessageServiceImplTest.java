package com.hxkj.wechatorders.service.impl;

import com.hxkj.wechatorders.dto.OrderDTO;
import com.hxkj.wechatorders.service.OrderService;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;


/**
 * Create by wangbin
 * 2018-10-31-1:06
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class PushMessageServiceImplTest {

    @Autowired
    private PushMessageServiceImpl pushMessageService;
    @Autowired
    private OrderService orderService;

    @Test
    public void orderStatus() throws Exception {
        OrderDTO orderDTO = orderService.findOne("1531850330024990869");
        pushMessageService.orderStatus(orderDTO);
    }
}