package com.hxkj.wechatorders.service.impl;

import com.hxkj.wechatorders.dto.OrderDTO;
import com.hxkj.wechatorders.service.OrderService;
import com.hxkj.wechatorders.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import static org.junit.Assert.*;

/**
 * Created by wangbin
 * 2018-08-13 11:41
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class PayServiceImplTest {

    @Autowired
    private PayService payServiceImpl;
    @Autowired
    private OrderService orderServiceImpl;

    @Test
    public void create() throws Exception{
        OrderDTO orderDTO = orderServiceImpl.findOne("1531850188099790158");
        payServiceImpl.create(orderDTO);
    }

    @Test
    public void refund() throws Exception{
        OrderDTO orderDTO = orderServiceImpl.findOne("1534224050307815566");
        payServiceImpl.refund(orderDTO);
    }
}