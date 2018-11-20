package com.hxkj.wechatorders.utils;

/**自定义金额校验工具类
 * 判断金额的精度
 * Created by wangbin
 * 2018-08-14 11:28
 */
public class MathUtil {

    public static final Double MONEY_RANGE = 0.01;

    /**
     * 比较2个金额是否相等
     * @param d1
     * @param d2
     * @return
     */
    public static Boolean equals(Double d1,Double d2){
        Double result = d1 - d2;
        if(result < MONEY_RANGE){
            return true;
        }else {
            return false;
        }
    }

}
