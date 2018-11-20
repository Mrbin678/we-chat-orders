package com.hxkj.wechatorders.exception;

import com.hxkj.wechatorders.enums.ResultEnum;
import lombok.Getter;

/**自定义异常,方便反馈给调用者
 * Created by wangbin
 * 2018-07-12 13:45
 */
@Getter//通过这个注解给该异常加上Get方法
public class SellException extends RuntimeException{
    //状态码
    private Integer code;

    public SellException(ResultEnum resultEnum) {
        super(resultEnum.getMsg());
        this.code = resultEnum.getCode();
    }

    public SellException(Integer code,String message){
        super(message);
        this.code = code;
    }
}
