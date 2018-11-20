package com.hxkj.wechatorders.service;

import com.hxkj.wechatorders.dataobject.ProductInfo;
import com.hxkj.wechatorders.dto.CartDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

/**
 * Created by wangbin
 * 2018-07-10 16:12
 */
public interface ProductService {

    ProductInfo findOne(String productId);

    /**
     * 查询所有在架商品列表
     * @return
     */
    List<ProductInfo> findUpAll();

    /**
     * 查询所有商品，涉及分页要传入Pageable对象,返回的是Page对象泛型
     * @param pageable
     * @return
     */
    Page<ProductInfo> findAll(Pageable pageable);

    ProductInfo save(ProductInfo productInfo);

    //加库存
    void increaseStock(List<CartDTO> cartDTOList);

    //减库存
    void decreaseStock(List<CartDTO> cartDTOList);

    //上架
    ProductInfo onSale(String productId);
    //下架
    ProductInfo offSale(String productId);
}
