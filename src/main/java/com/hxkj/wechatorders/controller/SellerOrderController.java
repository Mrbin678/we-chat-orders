package com.hxkj.wechatorders.controller;

import com.hxkj.wechatorders.dto.OrderDTO;
import com.hxkj.wechatorders.enums.ResultEnum;
import com.hxkj.wechatorders.exception.SellException;
import com.hxkj.wechatorders.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**
 * 卖家端订单
 * Create by wangbin
 * 2018-09-13-10:28
 */
@Controller
@RequestMapping("/seller/order")
@Slf4j
public class SellerOrderController {

    @Autowired
    private OrderService orderServiceImpl;

    /**
     * 方法说明：卖家端订单列表查询
     * @author wangbin
     * @date 2018/9/13 13:32
     * @param page ：第几页，从第一页开始(这里page和size都是必传参数，为了防止参数没有传，我们给其设置了默认值)
     * @param size ：一页有多少条数据
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @GetMapping("/list")
    public ModelAndView list(@RequestParam(value = "page",defaultValue = "1") Integer page,
                             @RequestParam(value="size",defaultValue = "10") Integer size,
                             Map<String,Object> map){

        PageRequest request = PageRequest.of(page-1,size);
        Page<OrderDTO> orderDTOPage = orderServiceImpl.findList(request);
        map.put("orderDTOPage",orderDTOPage);
        map.put("currentPage",page);
        map.put("size",size);
        return new ModelAndView("order/list",map);
    }
    /**
     * 方法说明：取消订单
     * @author wangbin
     * @date 2018/9/25 15:41
     * @param orderId
     * @param map
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @GetMapping("/cancel")
    public  ModelAndView cancel(@RequestParam("orderId") String orderId,
                                Map<String,Object> map){

        try{
            //取消订单之前需要先查询订单，查询的时候后有可能查不到订单抛出异常
            OrderDTO orderDTO = orderServiceImpl.findOne(orderId);
            //取消订单的时候有可能订单状态不正确，导致无法取消订单（只能取消新建状态的订单）
            orderServiceImpl.cancel(orderDTO);
        }catch (SellException e){
            log.error("[卖家端取消订单] 发生异常{}",e);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_CANCEL_SUCCESS.getMsg());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
    /**
     * 方法说明：完结订单
     * @author wangbin
     * @date 2018/9/25 15:42
     * @param orderId
     * @param map
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @GetMapping("/finish")
    public ModelAndView finished(@RequestParam("orderId") String orderId,
                                 Map<String,Object> map){
        try{
            OrderDTO orderDTO = orderServiceImpl.findOne(orderId);
            orderServiceImpl.finish(orderDTO);
        }catch (SellException e){
            log.error("[卖家端完结订单] 发生异常{}",e);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("msg",ResultEnum.ORDER_FINISH_SUCCESS.getMsg());
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }

    /**
     * 方法说明：订单详情
     * @author wangbin
     * @date 2018/9/25 15:42
     * @param orderId
     * @param map
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @GetMapping("/detail")
    public ModelAndView detail(@RequestParam("orderId") String orderId,
                               Map<String,Object> map){

        OrderDTO orderDTO = new OrderDTO();
        try{
            orderDTO = orderServiceImpl.findOne(orderId);
        }catch (SellException e){
            log.error("[卖家端查询订单] 发生异常{}",e);
            map.put("msg", e.getMessage());
            map.put("url","/sell/seller/order/list");
            return new ModelAndView("common/error",map);
        }
        map.put("orderDTO",orderDTO);
        return new ModelAndView("order/detail",map);
    }


}


