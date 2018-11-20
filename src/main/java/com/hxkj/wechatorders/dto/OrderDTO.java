package com.hxkj.wechatorders.dto;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.hxkj.wechatorders.dataobject.OrderDetail;
import com.hxkj.wechatorders.enums.OrderStatusEnum;
import com.hxkj.wechatorders.enums.PayStatusEnum;
import com.hxkj.wechatorders.utils.EnumUtil;
import com.hxkj.wechatorders.utils.serializer.Date2LongSerializer;
import lombok.Data;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**订单的数据传输类(DTO:data transfer object :专门在各个层传输数据)
 * Created by wangbin
 * 2018-07-12 11:36
 */
@Data
/**
 * 返回前端的字段中含有可选字段，加上这个@JsonInclude(JsonInclude.Include.NON_NULL)注解后
 * 可以将返回字段为空的可选字段过滤掉，不用返回给前端，
 * 一般情况下，可选字段为空，我们都会选择不返回给前端，当然如果对象是null,也会不返回
 * 如果这种情况比较多，那很多类上就要加这个注解，这样过于繁琐，可以选择加入全局的配置，
 * 到application.yml文件中进行全局的配置
 * 让返回的数据为空的话就过滤掉
 * 如果必须返回的字段是null的话，可以通过给字段设置初始默认值的方式返回给前端相应的格式数据
 */
//@JsonInclude(JsonInclude.Include.NON_NULL)
public class OrderDTO {

    /*订单主表id*/
    private  String orderId;
    /*买家姓名*/
    private String buyerName;
    /*买家电话*/
    private String buyerPhone;
    /*买家地址*/
    private String buyerAddress;
    /*买家微信openid*/
    private String buyerOpenid;
    /*订单总金额*/
    private BigDecimal orderAmount;
    /*订单状态，默认为0，新下单*/
    private Integer orderStatus;
    /*支付状态，默认为0，未支付*/
    private Integer payStatus;
    /*订单创建时间*/
    /**
     * 使用@JsonSerialize注解，传入我们自定义的转换时间的工具类，让其自动转换然后返回给前端
     */
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date createTime;
    /*订单更新时间，自动动态更新updateTime需要加上@DynamicUpdate注解*/
    @JsonSerialize(using = Date2LongSerializer.class)
    private Date updateTime;
    /*订单详情集合*/
    private List<OrderDetail> orderDetailList;
    /*使用JsonIgnore注解是防止写rest接口的时候对象转成json数据格式返回的时候会中含有getOrderStatusEnum和
    * getPayStatusEnum这两个字段，使用这个注解让对象转json数据返回的时候忽略掉这两个方法*/
    @JsonIgnore
    public OrderStatusEnum getOrderStatusEnum(){
        return EnumUtil.getByCode(orderStatus,OrderStatusEnum.class);
    }
    @JsonIgnore
    public PayStatusEnum getPayStatusEnum(){
        return EnumUtil.getByCode(payStatus,PayStatusEnum.class);
    }

}
