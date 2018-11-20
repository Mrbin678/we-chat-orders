package com.hxkj.wechatorders.dataobject;

import lombok.Data;
import org.hibernate.annotations.DynamicUpdate;
import javax.persistence.Entity;
import javax.persistence.Id;

/**卖家信息表
 * Create by wangbin
 * 2018-09-28-14:10
 */
@Entity
@DynamicUpdate
@Data
public class SellerInfo {
    @Id
    private String sellerId;
    private String username;
    private String password;
    private String openid;
}
