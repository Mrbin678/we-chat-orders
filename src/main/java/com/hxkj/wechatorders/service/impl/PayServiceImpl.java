package com.hxkj.wechatorders.service.impl;

import com.hxkj.wechatorders.dto.OrderDTO;
import com.hxkj.wechatorders.enums.ResultEnum;
import com.hxkj.wechatorders.exception.SellException;
import com.hxkj.wechatorders.service.OrderService;
import com.hxkj.wechatorders.service.PayService;
import com.hxkj.wechatorders.utils.JsonUtil;
import com.hxkj.wechatorders.utils.MathUtil;
import com.lly835.bestpay.enums.BestPayTypeEnum;
import com.lly835.bestpay.model.PayRequest;
import com.lly835.bestpay.model.PayResponse;
import com.lly835.bestpay.model.RefundRequest;
import com.lly835.bestpay.model.RefundResponse;
import com.lly835.bestpay.service.impl.BestPayServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;

/**
 * Created by wangbin
 * 2018-08-13 10:15
 */
@Service
@Slf4j
public class PayServiceImpl implements PayService{

    private static final String ORDER_NAME = "微信点餐订单";

    @Autowired
    private BestPayServiceImpl bestPayService;

    @Autowired
    private OrderService orderServiceImpl;

    @Override
    public PayResponse create(OrderDTO orderDTO) {
        PayRequest payRequest = new PayRequest();
        payRequest.setOpenid(orderDTO.getBuyerOpenid());
        payRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        payRequest.setOrderId(orderDTO.getOrderId());
        payRequest.setOrderName(ORDER_NAME);
        payRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信支付] 发起支付,request={}", JsonUtil.toJson(payRequest));

        PayResponse payResponse = bestPayService.pay(payRequest);
        log.info("[微信支付] 发起支付,response={}",JsonUtil.toJson(payResponse));
        return payResponse;

    }

    @Override
    public PayResponse notify(String notifyData) {
        //异步通知的注意事项
        /**
         * 1.验证签名（签名是出于安全性考虑根据验证程序的合法性）
         * 2.支付的状态（有可能支付不成功）
         * 3.支付的金额（有可能因为某些bug的原因出现订单金额与支付的金额不一致，需要校验，
         * 订单金额与支付金额一致的情况下，我们才更新订单的状态）
         * 4.支付人（下单人与支付人的关系，根绝业务需求判断是否需要本人支付还是允许代付，我们这里不做限制）
         * 前面2点已经由bestpay sdk 帮我们校验了，我们从第三点开始做
         */

        PayResponse payResponse = bestPayService.asyncNotify(notifyData);
        log.info("[微信支付] 异步通知,payResponse={}",JsonUtil.toJson(payResponse));

        //查询订单
        OrderDTO orderDTO = orderServiceImpl.findOne(payResponse.getOrderId());
        //判断订单是否存在
        if(orderDTO == null){
            log.error("[微信支付] 异步通知，订单不存在，orderId={}",payResponse.getOrderId());
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        //判断订单金额与支付金额是否一致(金额的比较不要用equals，系统金额是BigDecimal类型，微信通知金额是double类型，涉及类型转换
        // 这里比较金额使用compareTo,并且对微信通知金额进行类型转换,这里还要考虑金额精度的问题，比如0.10和0.1的比较)
        if(!MathUtil.equals(payResponse.getOrderAmount(),orderDTO.getOrderAmount().doubleValue())){
            log.error("[微信支付] 异步通知，订单金额不一致，orderId={}，微信通知金额={}，系统金额={}"
                    ,payResponse.getOrderId(),
                    payResponse.getOrderAmount(),
                    orderDTO.getOrderAmount());
            throw new SellException(ResultEnum.WXPAY_NOTIFY_MONEY_VERIFY_ERROR);
        }
        //修改订单的支付状态
        orderServiceImpl.Paid(orderDTO);
        return payResponse;
    }

    /**
     * 退款
     * @param orderDTO
     */
    @Override
    public RefundResponse refund(OrderDTO orderDTO) {
        RefundRequest refundRequest = new RefundRequest();
        refundRequest.setOrderId(orderDTO.getOrderId());
        refundRequest.setOrderAmount(orderDTO.getOrderAmount().doubleValue());
        refundRequest.setPayTypeEnum(BestPayTypeEnum.WXPAY_H5);
        log.info("[微信退款] request={}",JsonUtil.toJson(refundRequest));
        RefundResponse refundResponse = bestPayService.refund(refundRequest);
        log.info("[微信退款] response={}",JsonUtil.toJson(refundResponse));
        return refundResponse;
    }
}
