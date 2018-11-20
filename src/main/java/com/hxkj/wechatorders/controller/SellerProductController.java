package com.hxkj.wechatorders.controller;

import com.hxkj.wechatorders.dataobject.ProductCategory;
import com.hxkj.wechatorders.dataobject.ProductInfo;
import com.hxkj.wechatorders.dto.OrderDTO;
import com.hxkj.wechatorders.exception.SellException;
import com.hxkj.wechatorders.form.ProductForm;
import com.hxkj.wechatorders.service.CategoryService;
import com.hxkj.wechatorders.service.ProductService;
import com.hxkj.wechatorders.utils.KeyUtil;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**卖家端商品控制器
 * Create by wangbin
 * 2018-09-27-9:55
 */
@Controller
@RequestMapping("/seller/product")
public class SellerProductController {

    @Autowired
    private ProductService productServiceImpl;

    @Autowired
    private CategoryService categoryServiceImpl;

    /**
     * 方法说明：商品列表
     * @author wangbin
     * @date 2018/9/27 9:59
     * @param page
     * @param size
     * @param map
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value="size",defaultValue = "10") Integer size,
                             Map<String,Object> map){
        PageRequest request = PageRequest.of(page-1,size);
        Page<ProductInfo> productInfoPage = productServiceImpl.findAll(request);
        map.put("productInfoPage",productInfoPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("product/list",map);
    }
    /**
     * 方法说明：商品上架
     * @author wangbin
     * @date 2018/9/27 13:34
     * @param productId
     * @param map
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @GetMapping("/on_sale")
    public ModelAndView onSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){

        try{
            productServiceImpl.onSale(productId);
        }catch(SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("/common/error",map);
        }
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("common/success",map);
    }
    /**
     * 方法说明：商品下架
     * @author wangbin
     * @date 2018/9/27 14:04
     * @param productId
     * @param map
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @GetMapping("/off_sale")
    public ModelAndView offSale(@RequestParam("productId") String productId,
                               Map<String,Object> map){

        try{
            productServiceImpl.offSale(productId);
        }catch(SellException e){
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/product/list");
            return new ModelAndView("/common/error",map);
        }
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("common/success",map);
    }

    @GetMapping("/index")//这里producId不是必传的，所以设置required = false
    public ModelAndView index(@RequestParam(value = "productId",required = false) String productId,
                      Map<String,Object> map){
        if (!StringUtils.isEmpty(productId)){
            ProductInfo productInfo = productServiceImpl.findOne(productId);
            map.put("productInfo",productInfo);
        }
        //查询所有的类目
        List<ProductCategory> categoryList = categoryServiceImpl.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("product/index",map);
    }

    /**
     * 方法说明：更新、保存商品
     * @author wangbin
     * @date 2018/9/27 15:39
     * @param form
     * @param bindingResult
     * @param map
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @PostMapping("/save")
    //@CachePut(cacheNames = "product",key = "123")
    @CacheEvict(cacheNames = "product",key = "123")
    public ModelAndView save(@Valid ProductForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
        //验证表单是否出错，有错就跳到错误提示页面然后返回新增商品页面
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/product/index");
            return new ModelAndView("/common/error",map);
        }
        ProductInfo productInfo = new ProductInfo();
       try{
           //如果提交的表单中，商品ID不为空说明是更新商品信息
           if(!StringUtils.isEmpty(form.getProductId())){
               //表单验证通过后，就进行数据的拷贝，更新商品信息
               //这里特别要注意属性拷贝之前，先根据商品ID从数据库查询商品信息productInfo，
               // 然后用提交的form中的值覆盖数据库查询出来的productInfo的值，
                //form中不需要商品状态字段，
               //该字段我们有单独的操作，不然对象拷贝的时候会被覆盖成null(初始值)，
               // 保存或更新的时候就会出错
               productInfo = productServiceImpl.findOne(form.getProductId());
           }else{
               //商品ID为空说明是新增
               //商品ID为空的时候需要我们调用自己写好的工具类随机生成ID设置进表单然后拷贝到productInfo中，最后存到数据库
               form.setProductId(KeyUtil.genUniqueKey());
           }
           //对象拷贝将from表单中的值覆盖到productInfo中
           BeanUtils.copyProperties(form,productInfo);
           //保存或更新的时候如果出错也跳到错误提示页面，然后返回新增页面
           productServiceImpl.save(productInfo);
       }catch(SellException e){
           map.put("msg",e.getMessage());
           map.put("url","/sell/seller/product/index");
           return new ModelAndView("/common/error",map);
       }
       //保存或更新没出错跳转到成功页面然后回到商品列表页面
        map.put("url","/sell/seller/product/list");
        return new ModelAndView("/common/success",map);
    }
}
