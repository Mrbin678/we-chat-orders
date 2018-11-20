package com.hxkj.wechatorders.form;

import lombok.Data;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**前端提交过来的参数进行表单验证
 * Created by wangbin
 * 2018-07-17 14:06
 */
@Data
public class OrderForm {
    /**
     * 买家姓名
     */
    @NotEmpty(message="姓名必填")
    private String name;
    /**
     * 买家手机号
     */
    @NotEmpty(message="手机号码必填")
    private String phone;
    /**
     * 买家地址
     */
    @NotEmpty(message="地址必填")
    private String address;
    /**
     * 买家微信openid
     */
    @NotEmpty(message="openid必填")
    private String openid;
    /**
     *买家的购物车,购物车是json格式的字符，后面需要将其转换为我们需要个参数格式来使用
     * （需要用到json转换的pom依赖），com.google.code.gson
     */
    @NotEmpty(message="购物车不能为空")
    private String items;


}
