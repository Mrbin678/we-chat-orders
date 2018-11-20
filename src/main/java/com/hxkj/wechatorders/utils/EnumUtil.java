package com.hxkj.wechatorders.utils;

import com.hxkj.wechatorders.enums.CodeEnum;

/**枚举的工具类
 * Create by wangbin
 * 2018-09-13-16:53
 */
public class EnumUtil {

    public static <T extends CodeEnum> T getByCode(Integer code, Class<T> enumClass){
        for(T each:enumClass.getEnumConstants()){
            if(code.equals(each.getCode())){
                return each;
            }
        }
        return null;
    }
}
