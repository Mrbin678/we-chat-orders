package com.hxkj.wechatorders.dataobject;

import com.hxkj.wechatorders.enums.OrderStatusEnum;
import com.hxkj.wechatorders.enums.PayStatusEnum;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.Id;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

/**订单主表
 * Created by wangbin
 * 2018-07-11 15:27
 */
@Entity
@Data
@DynamicUpdate
public class OrderMaster {
    /*订单主表id*/
    @Id
    private String orderId;
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
    private Integer orderStatus = OrderStatusEnum.NEW.getCode();
    /*支付状态，默认为0，未支付*/
    private Integer payStatus = PayStatusEnum.WAIT.getCode();
    /*订单创建时间*/
    private Date createTime;
    /*订单更新时间，自动动态更新updateTime需要加上@DynamicUpdate注解*/
    private Date updateTime;

}
