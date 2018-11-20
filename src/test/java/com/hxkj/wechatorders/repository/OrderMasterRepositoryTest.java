package com.hxkj.wechatorders.repository;

import com.hxkj.wechatorders.dataobject.OrderMaster;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;
import java.math.BigDecimal;


/**
 * Created by wangbin
 * 2018-07-11 16:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class OrderMasterRepositoryTest {

    @Autowired
    private OrderMasterRepository repository;

    private final String OPENID = "110110";

    @Test
    public void saveTest(){
        OrderMaster orderMaster = new OrderMaster();
        orderMaster.setOrderId("1234567");
        orderMaster.setBuyerName("小刘");
        orderMaster.setBuyerPhone("13887621532");
        orderMaster.setBuyerAddress("恒芯科技股份有限公司5楼");
        orderMaster.setBuyerOpenid(OPENID);
        orderMaster.setOrderAmount(new BigDecimal(35.80));
        OrderMaster result = repository.save(orderMaster);
        Assert.assertNotNull(result);

    }


    @Test
    public void findByBuyerOpenid() throws Exception {
        /*传入page分页参数*/
        PageRequest request =  PageRequest.of(0,1);
        Page<OrderMaster> result = repository.findByBuyerOpenid(OPENID,request);
        System.out.println(result.getTotalElements());
        Assert.assertNotEquals(0, result.getTotalElements());
    }
}