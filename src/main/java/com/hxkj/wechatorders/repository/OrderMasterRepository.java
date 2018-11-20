package com.hxkj.wechatorders.repository;

import com.hxkj.wechatorders.dataobject.OrderMaster;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;


/**
 * 订单主体的dao层
 * Created by wangbin
 * 2018-07-11 16:20
 */
public interface OrderMasterRepository extends JpaRepository<OrderMaster,String>{
    /*通过买家的openid查询买家的订单主体并分页*/
    Page<OrderMaster> findByBuyerOpenid(String buyerOpenid, Pageable pageable);

}
