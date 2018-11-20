package com.hxkj.wechatorders.service;

import com.hxkj.wechatorders.dto.OrderDTO;

/**买家
 * Created by wangbin
 * 2018-07-19 0:33
 */
public interface BuyerService {
    //查询一个订单
    OrderDTO findOrderOne(String openid,String orderId);
    //取消订单
    OrderDTO cancelOrder(String openid,String orderId);
}
