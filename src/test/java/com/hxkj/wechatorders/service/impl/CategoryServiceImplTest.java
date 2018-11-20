package com.hxkj.wechatorders.service.impl;

import com.hxkj.wechatorders.dataobject.ProductCategory;
import com.hxkj.wechatorders.service.CategoryService;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import javax.persistence.Access;

import java.util.Arrays;
import java.util.List;

import static org.junit.Assert.*;

/**
 * Created by wangbin
 * 2018-07-10 13:35
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class CategoryServiceImplTest {

    @Autowired
    private CategoryService categoryServiceImpl;

    @Test
    public void findOne() throws Exception{
        ProductCategory productCategory = categoryServiceImpl.findOne(1);
       //断言希望查询到的对象id,和预期的id相同即通过测试
        Assert.assertEquals(new Integer(1),productCategory.getCategoryId());

    }

    @Test
    public void findAll() throws Exception {
        List<ProductCategory> productCategoryList = categoryServiceImpl.findAll();
        //断言不希望查询所有的类目是空的就通过
        Assert.assertNotEquals(0,productCategoryList.size());
    }

    @Test
    public void findByCategoryTypeIn() throws Exception{
        List<Integer> categoryTypeList = Arrays.asList(2,3,5);
        List<ProductCategory> productCategoryList = categoryServiceImpl.findByCategoryTypeIn(categoryTypeList);
        //断言不希望根据类目类型查询到的相关的类目是空的就通过
        Assert.assertNotEquals(0,productCategoryList.size());

    }

    @Test
    public void save() throws Exception{
        ProductCategory productCategory = new ProductCategory("店长推荐",6);
        //断言新增后返回的结果不为空就通过
        ProductCategory result = categoryServiceImpl.save(productCategory);
        Assert.assertNotNull(result);

    }
}