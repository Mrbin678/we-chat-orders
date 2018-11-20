package com.hxkj.wechatorders.dataobject.mapper;

import com.hxkj.wechatorders.dataobject.ProductCategory;
import org.apache.ibatis.annotations.*;

import java.util.List;
import java.util.Map;

/** 以类目为例，使用mybatis方式 对 类目 进行 CRUD （增删改查）
 * Create by wangbin
 * 2018-11-01-9:19
 */
public interface ProductCategoryMapper {

    @Insert("insert into product_category(category_name,category_type)" +
            "values(#{categoryName,jdbcType=VARCHAR}," +
            "#{categoryType,jdbcType=INTEGER})")
    //做添加操作的时候，每个对应的参数的参数类型需要跟着写在后面
    int insertByMap(Map<String,Object> map);


    @Insert("insert into product_category(category_name,category_type)" +
            "values(#{categoryName,jdbcType=VARCHAR}," +
            "#{categoryType,jdbcType=INTEGER})")
    int insertByObject(ProductCategory productCategory);

    //查询语句需要配置返回的而结果，数据库字段的值映射到对象的属性上，然后设置到返回的对象中
    //由于categoryType是做了约束的，不会重复所以查询到的结果只有一个类目对象
    @Select("select * from product_category where category_type = #{categoryType}")
    @Results({
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName"),
            @Result(column = "category_type",property = "categoryType")
    })
    ProductCategory findByCategoryType(Integer categoryType);

    //由于categoryName有可能重复，查询返回的类目可能有多个，所以用List集合
    @Select("select * from product_category where category_name = #{categoryName}")
    @Results({
            @Result(column = "category_id",property = "categoryId"),
            @Result(column = "category_name",property = "categoryName"),
            @Result(column = "category_type",property = "categoryType")
    })
    List<ProductCategory> findByCategoryName(String categoryName);

    @Update("update product_category set category_name=#{categoryName}" +
            "where category_type=#{categoryType}")
    //mybatis中传入多个参数的时候需要使用@Param注解，将值赋给里面的变量
    //通过某一个字段去更新
    int updateByCategoryType(@Param("categoryName") String categoryName,
                             @Param("categoryType") Integer categoryType);

    @Update("update product_category set category_name=#{categoryName}" +
            "where category_type=#{categoryType}")
    //通过一个对象去更新
    int updateByObject(ProductCategory productCategory);

    @Delete("delete from product_category where category_type = #{categoryType}")
    //通过categoryType来删除类目
    int deleteByCategoryType(Integer categoryType);

    ProductCategory selectByCategoryType(Integer categoryType);

}
