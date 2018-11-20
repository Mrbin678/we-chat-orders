package com.hxkj.wechatorders.repository;

import com.hxkj.wechatorders.dataobject.ProductCategory;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**类目的dao层，写一个接口继承JpaRepository接口，传入对应的对象和主键类型
 * 注意接口和类名的命名规则
 * Created by wangbin
 * 2018-07-10 9:32
 */
public interface ProductCategoryRepository extends JpaRepository<ProductCategory,Integer> {

    /*通过类目类型id集合查出类目类型集合*/
    List<ProductCategory> findByCategoryTypeIn(List<Integer> categoryTypeList);

}
