package com.hxkj.wechatorders.service.impl;

import com.hxkj.wechatorders.dataobject.OrderDetail;
import com.hxkj.wechatorders.dto.OrderDTO;
import com.hxkj.wechatorders.enums.OrderStatusEnum;
import com.hxkj.wechatorders.enums.PayStatusEnum;
import com.hxkj.wechatorders.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.weaver.ast.Or;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wangbin
 * 2018-07-16 11:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
@Slf4j
public class OrderServiceImplTest {

    @Autowired
    private OrderService orderServiceImpl;

    private final String BUYER_OPENID = "1101110";

    private final String ORDER_ID = "1531718725474698560";

    @Test
    public void createTest() throws Exception {
        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName("小王");
        orderDTO.setBuyerAddress("恒芯科技");
        orderDTO.setBuyerPhone("13990525642");
        orderDTO.setBuyerOpenid(BUYER_OPENID);
        //购物车
        List<OrderDetail> orderDetailList = new ArrayList<>();

        OrderDetail o1 = new OrderDetail();
        o1.setProductId("123456");
        o1.setProductQuantity(1);

        OrderDetail o2 = new OrderDetail();
        o2.setProductId("123457");
        o2.setProductQuantity(1);
        orderDetailList.add(o1);
        orderDetailList.add(o2);
        orderDTO.setOrderDetailList(orderDetailList);
        OrderDTO result = orderServiceImpl.create(orderDTO);
        log.info("[创建订单] result={}",result);

        Assert.assertNotNull(result);
    }

    @Test
    public void findOneTest() throws Exception {

        OrderDTO orderDTO = orderServiceImpl.findOne(ORDER_ID);
        log.info("[查询单个订单] result={}",orderDTO);
        Assert.assertEquals(ORDER_ID,orderDTO.getOrderId());

    }

    @Test
    public void findListTest() throws Exception {
        PageRequest request = new PageRequest(0,2);
        Page<OrderDTO> orderDTOPage = orderServiceImpl.findList(BUYER_OPENID,request);
        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());

    }

    @Test
    public void cancelTest() throws Exception{
        OrderDTO orderDTO = orderServiceImpl.findOne(ORDER_ID);
        OrderDTO result = orderServiceImpl.cancel(orderDTO);
        //断言取消订单后的订单状态为订单状态枚举中取消订单的状态码相同
        Assert.assertEquals(OrderStatusEnum.CANCEL.getCode(),result.getOrderStatus());
        //取消订单测试成功的依据：数据库中订单的状态由新下单变为已取消，
        // 订单购买的商品库存将会加回去涉及订单表订单状态的修改和商品表相应库存的返还修改

    }

    @Test
    public void finishTest() throws Exception{
        OrderDTO orderDTO = orderServiceImpl.findOne(ORDER_ID);
        OrderDTO result = orderServiceImpl.finish(orderDTO);
        //断言完结订单后的订单状态为订单状态枚举中完结订单的状态码相同
        Assert.assertEquals(OrderStatusEnum.FINISHED.getCode(),result.getOrderStatus());

    }

    @Test
    public void paidTest() throws Exception {
        OrderDTO orderDTO = orderServiceImpl.findOne(ORDER_ID);
        OrderDTO result = orderServiceImpl.Paid(orderDTO);
        //断言订单支付成功后的支付状态为支付状态枚举中支付成功的状态码相同
        Assert.assertEquals(PayStatusEnum.SUCCESS.getCode(),result.getPayStatus());
    }

    @Test
    public void list() throws Exception{
        //使用 PageRequest.of替换过时PageRequest(int page, int size)方法
        PageRequest request = PageRequest.of(0,2);
        Page<OrderDTO> orderDTOPage = orderServiceImpl.findList(request);
//        断言的2种写法
//        Assert.assertNotEquals(0,orderDTOPage.getTotalElements());
        Assert.assertTrue("查询所有的订单列表",orderDTOPage.getTotalElements() > 0);

    }
}