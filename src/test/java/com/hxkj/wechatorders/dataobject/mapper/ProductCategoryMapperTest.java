package com.hxkj.wechatorders.dataobject.mapper;

import com.hxkj.wechatorders.dataobject.ProductCategory;
import lombok.extern.slf4j.Slf4j;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 使用mybatis的方式 进行“类目”的CRUD，测试
 * Create by wangbin
 * 2018-11-01-9:32
 */
@RunWith(SpringRunner.class)
// 这里在测试的时候在@SpringBootTest后面加上(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
//让测试的时候随机分配端口启动项目真实环境，不然测试的时候会出现websocket初始化错误
//默认情况下, @SpringBootTest不会启动一个服务器，会导致websocket初始化错误
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@Slf4j
public class ProductCategoryMapperTest {
    //这里的mapper抛红是idea的一些问题，不影响我们的操作结果
    @Autowired
    private ProductCategoryMapper mapper;

    @Test
    public void insertByMap() throws Exception{
        Map<String,Object> map = new HashMap<>();
        map.put("categoryName","小叶喜欢");
        map.put("categoryType",110);
        int result = mapper.insertByMap(map);
        //期望返回的结果是1
        Assert.assertEquals(1,result);
    }

    @Test
    public void insertByObject() throws Exception{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("小叶不爱");
        productCategory.setCategoryType(111);
        int result = mapper.insertByObject(productCategory);
        Assert.assertEquals(1,result);
    }

    @Test
    public void findByCategoryType() throws Exception{
        ProductCategory result = mapper.findByCategoryType(111);
        Assert.assertNotNull(result);
    }

    @Test
    public void findByCategoryName() throws Exception{
        List<ProductCategory> result = mapper.findByCategoryName("小叶喜欢");
        Assert.assertNotEquals(0,result.size());
    }

    @Test
    public void updateByCategoryType() throws Exception{
        int result = mapper.updateByCategoryType("小叶不爱",111);
        Assert.assertEquals(1,result);
    }

    @Test
    public void updateByObject() throws Exception{
        ProductCategory productCategory = new ProductCategory();
        productCategory.setCategoryName("老爷子最爱");
        productCategory.setCategoryType(111);
        int result = mapper.updateByObject(productCategory);
        Assert.assertEquals(1,result);
    }

    @Test
    public void deleteByCategoryType(){
        int result = mapper.deleteByCategoryType(113);
        Assert.assertEquals(1,result);
    }

    @Test
    public void selectByCategoryType(){
        ProductCategory result = mapper.selectByCategoryType(111);
        Assert.assertNotNull(result);
    }

}