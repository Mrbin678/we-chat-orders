package com.hxkj.wechatorders.repository;

import com.hxkj.wechatorders.dataobject.OrderDetail;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

/**订单详情的dao层
 * Created by wangbin
 * 2018-07-11 16:31
 */
public interface OrderDetailRepository extends JpaRepository<OrderDetail,String> {
    /*通过订单ID查询订单详情*/
    List<OrderDetail> findByOrderId(String orderId);
}
