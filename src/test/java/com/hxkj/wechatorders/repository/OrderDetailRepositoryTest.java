package com.hxkj.wechatorders.repository;

import com.hxkj.wechatorders.dataobject.OrderDetail;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wangbin
 * 2018-07-12 10:25
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderDetailRepositoryTest {

    @Autowired
    private OrderDetailRepository repository;

    @Test
    public void saveTest(){

        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setDetailId("1234567810");
        orderDetail.setOrderId("123456002");
        orderDetail.setProductId("1110123");
        orderDetail.setProductIcon("http://xxx.jpg");
        orderDetail.setProductName("康师傅冰红茶");
        orderDetail.setProductPrice(new BigDecimal(3.59));
        orderDetail.setProductQuantity(1233);
        OrderDetail orderDetail1 = repository.save(orderDetail);
        Assert.assertNotNull(orderDetail1);
    }

    @Test
    public void findByOrderIdTest() throws Exception {
        List<OrderDetail> orderDetail = repository.findByOrderId("123456002");
        Assert.assertNotEquals(0,orderDetail.size());
    }
}