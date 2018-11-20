package com.hxkj.wechatorders.controller;

import com.hxkj.wechatorders.VO.ProductInfoVO;
import com.hxkj.wechatorders.VO.ProductVO;
import com.hxkj.wechatorders.VO.ResultVO;
import com.hxkj.wechatorders.dataobject.ProductCategory;
import com.hxkj.wechatorders.dataobject.ProductInfo;
import com.hxkj.wechatorders.service.CategoryService;
import com.hxkj.wechatorders.service.ProductService;
import com.hxkj.wechatorders.utils.ResultVOUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 买家商品相关的控制层
 * Created by wangbin
 * 2018-07-10 23:15
 * 由于返回的json格式的数据所以用
 * @RestController注解，这个注解相当于@Controller+@ResponseBody的组合注解
 */
@RestController
@RequestMapping("buyer/product")
public class BuyerProductController {

    @Autowired
    private ProductService productServiceImpl;

    @Autowired
    private CategoryService categoryServiceImpl;

    @GetMapping("/list")
    @Cacheable(cacheNames = "product",key = "123")
    public ResultVO list(){
        //1.查询所有的上架商品
        List<ProductInfo> productInfoList = productServiceImpl.findUpAll();
        //2.查询类目（一次性查询），查询的类目只查我们需要的，上架的商品集合分别对应的类目所组成的类目集合
//        List<Integer> categoryTypeList = new ArrayList<>();
//        //low的做法(传统方法,传统for循环)
//        for(ProductInfo productInfo:productInfoList){
//            categoryTypeList.add(productInfo.getCategoryType())
//        }
        //精简做法（java8，lambda表达式，比较强大）
        // (将我们的productInfoList集合转换成流，从中获取CategoryType，然后再收集成一个List集合)
        List<Integer> categoryTypeList = productInfoList.stream()
                .map(e -> e.getCategoryType())
                .collect(Collectors.toList());
        List<ProductCategory> productCategoryList = categoryServiceImpl.findByCategoryTypeIn(categoryTypeList);
        //3.数据拼装（这里是难点和重点），要点：不要把数据库的查询放到循环中去，不然负载过大，时间开销也大

        //这里是个双重for循环，先遍历类目，再遍历商品信息
        //商品集合包含类目
        List<ProductVO> productVOList = new ArrayList<>();
        //遍历类目
        for(ProductCategory productCategory:productCategoryList){
            ProductVO productVO = new ProductVO();
            productVO.setCategoryName(productCategory.getCategoryName());
            productVO.setCategoryType(productCategory.getCategoryType());
            //商品信息集合
            List<ProductInfoVO> productInfoVOList = new ArrayList<>();
            //遍历商品信息
            for(ProductInfo productInfo:productInfoList){
                if(productInfo.getCategoryType().equals(productCategory.getCategoryType())){
                    ProductInfoVO productInfoVO = new ProductInfoVO();
                    //使用这个方法将遍历对象的属性值拷贝到新的对象中（前提是属性名对应一致，不一致的属性不会处理）
                    // 这种做法就省略了set 很多属性的步骤
                    BeanUtils.copyProperties(productInfo,productInfoVO);
                    productInfoVOList.add(productInfoVO);
                }
            }
            productVO.setProductInfoVOList(productInfoVOList);
            productVOList.add(productVO);
        }
        return ResultVOUtil.success(productVOList);
    }


}
