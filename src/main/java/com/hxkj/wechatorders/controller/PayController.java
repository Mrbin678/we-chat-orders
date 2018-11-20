package com.hxkj.wechatorders.controller;

import com.hxkj.wechatorders.dto.OrderDTO;
import com.hxkj.wechatorders.enums.ResultEnum;
import com.hxkj.wechatorders.exception.SellException;
import com.hxkj.wechatorders.service.OrderService;
import com.hxkj.wechatorders.service.PayService;
import com.lly835.bestpay.model.PayResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import java.util.Map;

/**微信支付模块（调用的第三方sdk来进行的开发）
 * Created by wangbin
 * 2018-08-13 10:03
 */
@Controller
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private OrderService orderServiceImpl;
    @Autowired
    private PayService payServiceImpl;

    /**
     * 创建预支付订单，调用统一JSAPI，发起支付
     * @param orderId
     * @param returnUrl
     * @param map
     * @return
     */
    @GetMapping("/create")
    public ModelAndView create(@RequestParam("orderId") String orderId,
                               @RequestParam("returnUrl") String returnUrl,
                               Map<String,Object> map){
        //1.查询订单
        OrderDTO orderDTO = orderServiceImpl.findOne(orderId);
        if(orderDTO == null){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //2.发起支付
        PayResponse payResponse = payServiceImpl.create(orderDTO);
        map.put("payResponse",payResponse);
        map.put("returnUrl",returnUrl);
        return new ModelAndView("pay/create",map);
    }

    /**
     * 微信异步通知
     * @param notifyData
     */
    @PostMapping("/notify")
    public ModelAndView notify(@RequestBody String notifyData){
        //处理微信端发给我们的异步通知
        payServiceImpl.notify(notifyData);
        //返回给微信端处理结果
        return new ModelAndView("pay/success");
    }

}
