package com.hxkj.wechatorders.enums;

import lombok.Getter;

/**订单状态枚举,用于持久订单的状态
 * Created by wangbin
 * 2018-07-11 15:40
 */
@Getter
public enum OrderStatusEnum implements CodeEnum {

    NEW(0,"新订单"),
    FINISHED(1,"完结"),
    CANCEL(2,"已取消"),
    ;

    private Integer code;
    private String msg;

    OrderStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
