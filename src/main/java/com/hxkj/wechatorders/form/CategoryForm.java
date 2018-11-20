package com.hxkj.wechatorders.form;

import lombok.Data;

/**类目提交表单
 * Create by wangbin
 * 2018-09-28-9:53
 */
@Data
public class CategoryForm {

    private Integer categoryId;
    /*类目名称*/
    private String categoryName;
    /*类目编号*/
    private Integer categoryType;

}
