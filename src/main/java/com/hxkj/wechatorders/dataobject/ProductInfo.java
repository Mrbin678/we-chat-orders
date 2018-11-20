package com.hxkj.wechatorders.dataobject;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.hxkj.wechatorders.enums.ProductStatusEnum;
import com.hxkj.wechatorders.utils.EnumUtil;
import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;


import javax.persistence.Entity;
import javax.persistence.Id;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by wangbin
 * 2018-07-10 15:00
 */
@Entity
@Data
@DynamicUpdate //这个注解用于自动更新修改时间
public class ProductInfo implements Serializable {

    private static final long serialVersionUID = -1819709134466579533L;
    @Id
    private String productId;
    /*商品名称*/
    private String productName;
    /*商品单价*/
    private BigDecimal productPrice;
    /*库存*/
    private Integer productStock;
    /*描述*/
    private String productDescription;
    /*图片*/
    private String productIcon;
    /*状态.0：正常，1：下架*/  //新增的时候默认就是在架的状态
    private Integer productStatus = ProductStatusEnum.UP.getCode();
    /*类目编号*/
    private Integer categoryType;
    /*创建时间*/
    private Date createTime;
    /*修改时间*/
    private Date updateTime;

//    public ProductInfo() {
//
//    }
    @JsonIgnore//加上这个注解，作为json返回的时候忽略掉这个字段
    public ProductStatusEnum getProductStatusEnum(){
        return EnumUtil.getByCode(productStatus,ProductStatusEnum.class);
    }
}
