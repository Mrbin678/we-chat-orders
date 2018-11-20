package com.hxkj.wechatorders.service.impl;

import com.hxkj.wechatorders.dto.OrderDTO;
import com.hxkj.wechatorders.enums.ResultEnum;
import com.hxkj.wechatorders.exception.SellException;
import com.hxkj.wechatorders.service.BuyerService;
import com.hxkj.wechatorders.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

/**
 * Created by wangbin
 * 2018-07-19 0:36
 */
@Service
@Slf4j
public class BuyerServiceImpl implements BuyerService {

    @Autowired
    private OrderService orderServiceImpl;

    @Override
    public OrderDTO findOrderOne(String openid, String orderId) {
        return checkOrderOwner(openid, orderId);
    }

    @Override
    public OrderDTO cancelOrder(String openid, String orderId) {
        OrderDTO orderDTO = checkOrderOwner(openid, orderId);
        if(orderDTO == null){
            log.error("[取消订单] 查询不到该订单,orderId = {}",orderId);
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        return orderServiceImpl.cancel(orderDTO);
    }

    private OrderDTO checkOrderOwner(String openid, String orderId){

        if(StringUtils.isEmpty(openid)){
            log.error("[订单详情查询] openid为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }else if(StringUtils.isEmpty(orderId)){
            log.error("[订单详情查询] orderId为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }

        OrderDTO orderDTO = orderServiceImpl.findOne(orderId);
        if(orderDTO == null){
            return null;
        }
        //equalsIgnoreCase忽略大小写比较openid是否一致,判断是否是自己的订单
        if(!orderDTO.getBuyerOpenid().equalsIgnoreCase(openid)){
            log.error("[查询订单] 订单的openid不一致.openid = {},orderDTO = {}",openid,orderDTO);
            throw new SellException(ResultEnum.ORDER_OWNER_ERROR);
        }
        return orderDTO;
    }
}
