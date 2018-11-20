package com.hxkj.wechatorders.dataobject.dao;

import com.hxkj.wechatorders.dataobject.mapper.ProductCategoryMapper;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Map;

/**
 * Create by wangbin
 * 2018-11-01-14:01
 */
public class ProductCategoryDao {

    @Autowired
    private ProductCategoryMapper mapper;

    public int insertByMap(Map<String,Object> map){
        return mapper.insertByMap(map);
    }
}
