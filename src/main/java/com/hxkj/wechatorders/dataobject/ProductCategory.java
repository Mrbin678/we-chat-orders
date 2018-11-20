package com.hxkj.wechatorders.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.Date;

/**
 * 类目
 * Created by wangbin
 * 2018-07-10 9:14
 * spring-data-jpa 数据库表名与对应的类名规则：
 * 数据库表名：product_category   类名采用驼峰式命名法：ProductCategory
 * 如果表名和类名不是像这样保持一致
 * 例如：表名是：s_product_category,
 * 那么我们就要在对应的类上加上注解：@Table(name="s_product_category")
 * @Entity:设置实体类和表进行映射（V0层的实体类不用做映射，所以不需要加这个注解）
 * 由于下载了lombok插件，引入了lombok依赖，这里直接用
 * @Data注解直接帮我们生成get,set以及toString方法（需要limbok依赖的引入和插件的下载）
 * 如果我们只希望保留get或set方法也可以使用@Getter或@Setter注解来完成
 * @DynamicUpdate:动态更新，自动更新修改时间（使用这个注解可以自动帮我们在更新数据的时候
 * 自动帮我们更新数据最新的更新时间，如果没加这个注解很有可能最新的更新时间并没有设置进去，
 * 具体示例看saveTest中的讲解，这个注解很重要，要注意）
 * @DynamicInsert:注解与@DynamicUpdate同理，自动设置新增的时间
 */
@Entity
@DynamicUpdate
@Data
public class ProductCategory {
    /* 类目id.设置id自增,声明主键并自增*/
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer categoryId;
    /*类目名称*/
    private String categoryName;
    /*类目编号*/
    private Integer categoryType;
    /*创建时间*/
    private Date createTime;
    /*修改时间*/
    private Date updateTime;

    public ProductCategory() {

    }

    public ProductCategory(String categoryName, Integer categoryType) {
        this.categoryName = categoryName;
        this.categoryType = categoryType;
    }
}
