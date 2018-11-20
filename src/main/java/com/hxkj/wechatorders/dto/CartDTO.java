package com.hxkj.wechatorders.dto;

import lombok.Data;

/**
 * 购物车DTO（Data Transfer Object:数据传输对象）
 * Created by wangbin
 * 2018-07-16 10:13
 */
@Data
public class CartDTO {
    /*商品id*/
    private String productId;
    /*商品数量*/
    private Integer productQuantity;

    public CartDTO(String productId, Integer productQuantity) {
        this.productId = productId;
        this.productQuantity = productQuantity;
    }
}
