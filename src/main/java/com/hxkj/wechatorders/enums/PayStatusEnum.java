package com.hxkj.wechatorders.enums;

import lombok.Getter;


/**支付状态枚举类，用于持久支付的状态
 * Created by wangbin
 * 2018-07-11 15:58
 */
@Getter
public enum PayStatusEnum implements CodeEnum{

    WAIT(0,"等待支付"),
    SUCCESS(1,"支付成功"),
    ;
    private Integer code;
    private String msg;

    PayStatusEnum(Integer code, String msg) {
        this.code = code;
        this.msg = msg;
    }
}
