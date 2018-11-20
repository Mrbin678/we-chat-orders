package com.hxkj.wechatorders.service;

import com.hxkj.wechatorders.dataobject.ProductCategory;

import java.util.List;

/**
 * Created by wangbin
 * 2018-07-10 13:17
 */
public interface CategoryService {

    ProductCategory findOne(Integer categoryId);

    List<ProductCategory> findAll();

    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

    /**
     * 新增和更新都是save方法，就看传入的对象中是否含有id，有主键id就是更新，没有主键id就是新增
     * @param productCategory
     * @return
     */
    ProductCategory save(ProductCategory productCategory);






}
