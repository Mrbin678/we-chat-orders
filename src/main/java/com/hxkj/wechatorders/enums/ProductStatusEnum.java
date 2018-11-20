package com.hxkj.wechatorders.enums;

import lombok.Getter;

/**
 * Created by wangbin
 * 2018-07-10 16:26
 * 商品状态 枚举类型 用来持久商品的状态
 */
@Getter
public enum ProductStatusEnum implements CodeEnum{

    UP(0,"在架"),
    DOWN(1,"下架"),
    ;

    private Integer code;
    private String message;

    ProductStatusEnum(Integer code,String message) {
        this.code = code;
        this.message = message;
    }
}
