package com.hxkj.wechatorders.service.impl;

import com.hxkj.wechatorders.dataobject.ProductCategory;
import com.hxkj.wechatorders.repository.ProductCategoryRepository;
import com.hxkj.wechatorders.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**类目service接口实现类
 * Created by wangbin
 * 2018-07-10 13:24
 * service层的实现类要使用
 * @Service注解标注
 */
@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    private ProductCategoryRepository repository;

    @Override
    public ProductCategory findOne(Integer categoryId) {
        return repository.findById(categoryId).get();
    }

    @Override
    public List<ProductCategory> findAll() {
        return repository.findAll();
    }

    @Override
    public List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList) {
        return repository.findByCategoryTypeIn(categoryTypeList);
    }

    @Override
    public ProductCategory save(ProductCategory productCategory) {
        return repository.save(productCategory);
    }
}
