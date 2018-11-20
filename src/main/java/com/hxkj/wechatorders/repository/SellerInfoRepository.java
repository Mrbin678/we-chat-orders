package com.hxkj.wechatorders.repository;

import com.hxkj.wechatorders.dataobject.SellerInfo;
import org.springframework.data.jpa.repository.JpaRepository;

/**卖家信息dao层
 * Create by wangbin
 * 2018-09-28-14:16
 */
public interface SellerInfoRepository extends JpaRepository<SellerInfo,String> {

    SellerInfo findByOpenid(String openid);

}
