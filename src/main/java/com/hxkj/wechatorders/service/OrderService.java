package com.hxkj.wechatorders.service;

import com.hxkj.wechatorders.dataobject.OrderMaster;
import com.hxkj.wechatorders.dto.OrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**订单service层
 * Created by wangbin
 * 2018-07-12 10:52
 */
public interface OrderService {

    /*创建订单*/
    OrderDTO create(OrderDTO orderDTO);
    /*查询单个订单*/
    OrderDTO findOne(String orderId);
    /*查询订单列表(某个人的订单列表),涉及分页.买家端只需要看自己的订单列表*/
    Page<OrderDTO> findList(String buyerOpenid, Pageable pageable);

    /**
     * 下面三个方法都是改变订单的状态
     * @param orderDTO
     * @return
     */
    /*取消订单*/
    OrderDTO cancel(OrderDTO orderDTO);
    /*结束订单*/
    OrderDTO finish(OrderDTO orderDTO);
    /*支付订单*/
    OrderDTO Paid(OrderDTO orderDTO);

    /*查询所有人的订单.卖家端.需要查看所有人的订单，涉及分页*/
    Page<OrderDTO> findList( Pageable pageable);


}
