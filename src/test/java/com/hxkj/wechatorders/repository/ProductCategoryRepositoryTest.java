package com.hxkj.wechatorders.repository;

import com.hxkj.wechatorders.dataobject.ProductCategory;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.transaction.annotation.Transactional;
import java.util.Arrays;
import java.util.List;


/**
 * Created by wangbin
 * 2018-07-10 9:37
 * 单元测试接口
 * 用到的注解:
 * @RunWith(SpringRunner.class)
 * @SpringBootTest
 * @Test
 * @Autowired
 * spring-data-jpa 封装了一些基本的对象操作不用写实体类直接继承接口调用方法来实现对数据库的操作
 */
@RunWith(SpringRunner.class)
@SpringBootTest
public class ProductCategoryRepositoryTest {
    @Autowired
    private ProductCategoryRepository repository;
    @Test
    public void  findOneTest(){
        /*测试根据id查询类目*/
        ProductCategory productCategory = repository.findById(1).get();
        System.out.println(productCategory.toString());
    }
    @Test
    @Transactional
    /**
     * 当我们希望测试数据不影响数据库的时候可以在单元测试的方法上再加一个注解
     * @Transactional，保证我们测试完后，测试数据不影响数据库，测试完就回滚事务，数据就没添加到数据库
     */
    /*在spring-data-jpa中新增和更新都是调用save方法，只不过更新需要传ID ，新增不用*/
    public void saveTest(){
        /**
         * 一般更新之前我们会先把更新之前的数据查出来然后你进行权限等判断，再更新，
         * 然后要动态的更新时间，由于我们查出来的数据里面有以前的更新时间，我们现在更新的时候
         * 又没有把新的更新时间填进去，由此我们跟新数据的时候就没把新的更新时间设置进去，更新时间
         * 还是以前数据的时间，这显然不是我们想要的，因此我们在对应的实体类加入了
         * @DynamicUpdate 注解，让我们更新数据的时候自动帮我们更新数据最新的更新时间
         * @DinamicInsert 注解，同理
         */
//        ProductCategory productCategory = repository.findById(2).get();
        /*这里如果设置了ID就是更新，没设置ID 就是新增，ID是自增长的*/
//        productCategory.setCategoryId(2);
//        productCategory.setCategoryName("男生最爱");
//        productCategory.setCategory_type(5);

        ProductCategory productCategory = new ProductCategory("女生最爱",3);
        ProductCategory result = repository.save(productCategory);
        Assert.assertNotNull(result);
        //断言结果，不希望他是null，希望他是result，下面这个方法和上面的一样
//        Assert.assertNotEquals(null,result);

    }
    @Test
    public void findByCategoryTypeInTest(){
        List<Integer> list = Arrays.asList(2,3,5);
        List<ProductCategory> result = repository.findByCategoryTypeIn(list);
        Assert.assertNotEquals(0,result.size());

    }


}