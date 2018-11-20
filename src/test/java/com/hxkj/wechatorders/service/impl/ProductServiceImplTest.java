package com.hxkj.wechatorders.service.impl;

import com.hxkj.wechatorders.dataobject.ProductInfo;
import com.hxkj.wechatorders.enums.ProductStatusEnum;
import com.hxkj.wechatorders.service.ProductService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.test.context.junit4.SpringRunner;

import java.math.BigDecimal;
import java.util.List;



/**
 * Created by wangbin
 * 2018-07-10 16:38
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductServiceImplTest {

    @Autowired
    private ProductService productServiceImpl;

    @Test
    public void findOneTest() throws  Exception {
        ProductInfo productInfo =  productServiceImpl.findOne("123456");
        Assert.assertEquals("123456",productInfo.getProductId());
    }

    @Test
    public void findUpAll() throws  Exception {
        List<ProductInfo> productInfoList = productServiceImpl.findUpAll();
        Assert.assertNotEquals(0,productInfoList.size());
    }

    @Test
    public void findAll() throws  Exception{
        PageRequest request = PageRequest.of(0,2);
        Page<ProductInfo> productInfoPage = productServiceImpl.findAll(request);
        System.out.println(productInfoPage.getTotalElements());
        Assert.assertNotEquals(0,productInfoPage.getTotalElements());
    }

    @Test
    public void save() throws  Exception {
        ProductInfo productInfo = new ProductInfo();
        productInfo.setProductId("123457");
        productInfo.setProductName("酸梅汤");
        productInfo.setProductPrice(new BigDecimal(2.8));
        productInfo.setProductStock(500);
        productInfo.setProductDescription("美味解渴");
        productInfo.setProductIcon("http://xxx.jpg");
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        productInfo.setCategoryType(2);
        ProductInfo result = productServiceImpl.save(productInfo);
        //不希望结果为空
        Assert.assertNotNull(result);
    }

    @Test
    public void onSale(){
        ProductInfo result = productServiceImpl.onSale("123456");
        Assert.assertEquals(ProductStatusEnum.UP,result.getProductStatusEnum());
    }

    @Test
    public void offSale(){
        ProductInfo result = productServiceImpl.offSale("123456");
        Assert.assertEquals(ProductStatusEnum.DOWN,result.getProductStatusEnum());
    }



}