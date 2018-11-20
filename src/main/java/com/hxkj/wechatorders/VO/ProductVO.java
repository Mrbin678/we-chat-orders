package com.hxkj.wechatorders.VO;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 商品包含类目（返回给前端视图层的实体类，区分和数据库做表映射的实体类）
 * Created by wangbin
 * 2018-07-11 0:08
 * 由于返回给前端的内容比较复杂不好区分
 * 这里使用@JsonProperty注解让对象序列化的时候我们自己定义的属性名称变成注解里面设置的名称
 * 一般出于安全的需要，前端需要几个字段就返回几个字段，一般不要返回多余的属性，所以一般不用
 * 我们映射数据数据库表的实体类作为返回对象，而是重新根据前端的需要重新定义新的对应起前端视图
 * 需要的实体类，所以这里我们要新建ProductInfoVO实体类来对应前端视图需要的商品详情实体类
 */
@Data
public class ProductVO implements Serializable {
    //在idea中下载了SerialVersionUID插件，设置了快捷键shift+I，自动生成序列化ID
    private static final long serialVersionUID = 7334337391178360460L;
    @JsonProperty("name")
    private String categoryName;
    @JsonProperty("type")
    private Integer categoryType;
    @JsonProperty("foods")
    private List<ProductInfoVO> productInfoVOList;

}
