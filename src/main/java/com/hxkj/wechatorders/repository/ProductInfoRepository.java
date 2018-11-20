package com.hxkj.wechatorders.repository;

import com.hxkj.wechatorders.dataobject.ProductInfo;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**
 * Created by wangbin
 * 2018-07-10 15:10
 */
public interface ProductInfoRepository extends JpaRepository<ProductInfo,String> {
    /*通过商品的状态查询商品*/
    List<ProductInfo> findByProductStatus(Integer productStatus);
}
