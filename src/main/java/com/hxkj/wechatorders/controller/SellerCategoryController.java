package com.hxkj.wechatorders.controller;

import com.hxkj.wechatorders.dataobject.ProductCategory;
import com.hxkj.wechatorders.exception.SellException;
import com.hxkj.wechatorders.form.CategoryForm;
import com.hxkj.wechatorders.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**卖家类目控制器
 * Create by wangbin
 * 2018-09-27-16:50
 */
@Controller
@RequestMapping("/seller/category")
public class SellerCategoryController {

    @Autowired
    private CategoryService categoryServiceImpl;
    /**
     * 方法说明：类目列表展示，数目较少，无分页
     * @author wangbin
     * @date 2018/9/28 9:35
     * @param map
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @GetMapping("/list")
    public ModelAndView list(Map<String,Object> map){

        List<ProductCategory> categoryList = categoryServiceImpl.findAll();
        map.put("categoryList",categoryList);
        return new ModelAndView("category/list",map);
    }

    /**
     * 方法说明：修改类目时的页面展示
     * @author wangbin
     * @date 2018/9/28 9:37
     * @param categoryId
     * @param map
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @GetMapping("/index") //categoryId是非必填的，所以 required = false
    public ModelAndView index(@RequestParam(value = "categoryId",required = false) Integer categoryId,
                              Map<String,Object> map){

       //categoryId 不为空，说明类目存在
        if(categoryId != null){
            //categoryId不为空的情况下查询类目
            ProductCategory productCategory = categoryServiceImpl.findOne(categoryId);
            map.put("category",productCategory);
       }
       //跳转到类目展示页面
       return new ModelAndView("category/index",map);
    }
    /**
     * 方法说明：新增或更新 类目
     * @author wangbin
     * @date 2018/9/28 9:56
     * @param form
     * @param bindingResult
     * @param map
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @PostMapping("/save")//提交类目表单，@Valid验证表单内容是否通过
    public ModelAndView save(@Valid CategoryForm form,
                             BindingResult bindingResult,
                             Map<String,Object> map){
       //表单提交后如果有错误的话就跳转到错误提示页面，然后返回新增类目页面
        if(bindingResult.hasErrors()){
            map.put("msg",bindingResult.getFieldError().getDefaultMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("/common/error",map);
        }
        ProductCategory productCategory= new ProductCategory();
        try{
            if(form.getCategoryId()!=null){
                productCategory = categoryServiceImpl.findOne(form.getCategoryId());
            }
            //属性拷贝
            BeanUtils.copyProperties(form,productCategory);
            //由于category中字段我们设置了integer自增注解，所以不用判断是否是新增还是修改设置id
            categoryServiceImpl.save(productCategory);
        }catch(SellException e){
            //如果出现错误就进入错误提示页面，然后跳转回修改页面
            map.put("msg",e.getMessage());
            map.put("url","/sell/seller/category/index");
            return new ModelAndView("/common/error",map);
        }
        map.put("url","/sell/seller/category/list");
        //成功就进入成功提示页面，然后跳转回类目列表页面
        return new ModelAndView("/common/success",map);
    }

}
