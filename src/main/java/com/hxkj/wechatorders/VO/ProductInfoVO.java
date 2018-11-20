package com.hxkj.wechatorders.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.math.BigDecimal;

/** 前端是视图层需要的商品信息实体类
 * Created by wangbin
 * 2018-07-11 0:25
 */
@Data
public class ProductInfoVO implements Serializable {

    //在idea中下载了SerialVersionUID插件，设置了快捷键shift+I，自动生成序列化ID
    private static final long serialVersionUID = 8913483976832867573L;

    @JsonProperty("id")
    /*商品id*/
    private String productId;
    @JsonProperty("name")
    /*商品名称*/
    private String productName;
    @JsonProperty("price")
    /*商品单价*/
    private BigDecimal productPrice;
    @JsonProperty("description")
    /*商品描述*/
    private String productDescription;
    @JsonProperty("icon")
    /*商品图片*/
    private String productIcon;

}
