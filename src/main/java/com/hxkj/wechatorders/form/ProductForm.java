package com.hxkj.wechatorders.form;

import lombok.Data;

import javax.persistence.Id;
import java.math.BigDecimal;

/**表单提交专用的商品表单
 * Create by wangbin
 * 2018-09-27-15:32
 */
@Data
public class ProductForm {

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
    /*类目编号*/
    private Integer categoryType;




}



