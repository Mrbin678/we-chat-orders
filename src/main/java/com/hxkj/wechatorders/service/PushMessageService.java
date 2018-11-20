package com.hxkj.wechatorders.service;

import com.hxkj.wechatorders.dto.OrderDTO;

/** 推送消息
 * Create by wangbin
 * 2018-10-31-0:38
 */
public interface PushMessageService {
    /**
     * 方法说明：订单状态变更消息
     * @author wangbin
     * @date 2018/10/31 0:39
     * @param orderDTO
     * @return 
     * @throws 
     */
    void orderStatus(OrderDTO orderDTO);


}
