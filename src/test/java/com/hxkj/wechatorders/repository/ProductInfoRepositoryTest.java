package com.hxkj.wechatorders.repository;

import com.hxkj.wechatorders.dataobject.ProductInfo;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;


/**
 * Created by wangbin
 * 2018-07-10 15:15
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductInfoRepositoryTest {

    @Autowired
    private  ProductInfoRepository repository;

    @Test
    public void saveTest(){
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123456");
        productInfo.setProductName("红牛饮料");
        productInfo.setProductPrice(new BigDecimal(5.5));
        productInfo.setProductStock(500);
        productInfo.setProductDescription("功能饮料");
        productInfo.setProductIcon("http://xxx.jpg");
        productInfo.setProductStatus(0);
        productInfo.setCategoryType(2);
        ProductInfo result = repository.save(productInfo);
        //不希望结果为空
        Assert.assertNotNull(result);
    }

    @Test
    public void findByProductStatusTest() throws  Exception{

        List<ProductInfo> productInfoList = repository.findByProductStatus(0);
        //不希望查询到正常的商品为0
        Assert.assertNotEquals(0,productInfoList.size());

    }
}