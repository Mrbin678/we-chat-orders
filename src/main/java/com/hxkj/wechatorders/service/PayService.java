package com.hxkj.wechatorders.service;

import com.hxkj.wechatorders.dto.OrderDTO;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundResponse;

/**
 * 微信支付服务层
 * Created by wangbin
 * 2018-08-13 10:13
 */
public interface PayService {

    PayResponse create(OrderDTO orderDTO);

    PayResponse notify(String notifyData);

    RefundResponse refund(OrderDTO orderDTO);


}
