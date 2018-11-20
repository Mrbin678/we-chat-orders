package com.hxkj.wechatorders.service.impl;

import com.hxkj.wechatorders.config.WechatAccountConfig;
import com.hxkj.wechatorders.dto.OrderDTO;
import com.hxkj.wechatorders.service.PushMessageService;
import lombok.extern.slf4j.Slf4j;
import me.chanjar.weixin.common.error.WxErrorException;
import me.chanjar.weixin.mp.api.WxMpService;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateData;
import me.chanjar.weixin.mp.bean.template.WxMpTemplateMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Arrays;
import java.util.List;

/**
 * Create by wangbin
 * 2018-10-31-0:42
 */
@Service
@Slf4j
public class PushMessageServiceImpl implements PushMessageService {

    @Autowired
    private WxMpService wxMpService;
    @Autowired
    private WechatAccountConfig accountConfig;

    @Override
    public void orderStatus(OrderDTO orderDTO) {
        WxMpTemplateMessage templateMessage = new WxMpTemplateMessage();
        templateMessage.setTemplateId(accountConfig.getTemplateId().get("orderStatus"));//模板ID,微信公众号申请后会有一个模板ID
        templateMessage.setToUser(orderDTO.getBuyerOpenid());//推动给谁（接收者的openid）
        //封装推送的消息
        List<WxMpTemplateData> data = Arrays.asList(
//                new WxMpTemplateData("first","亲，记得收货"),
//                new WxMpTemplateData("keyword1","微信点餐"),
//                new WxMpTemplateData("keyword2","13990525693"),
//                new WxMpTemplateData("keyword3",orderDTO.getOrderId()),
//                new WxMpTemplateData("keyword4",orderDTO.getOrderStatusEnum().getMsg() ),
//                new WxMpTemplateData("keyword5","￥"+orderDTO.getOrderAmount()),
//                new WxMpTemplateData("remark","欢迎再次光临!")
                new WxMpTemplateData("first","亲，记得收货"),
                new WxMpTemplateData("keyword1",orderDTO.getBuyerName()),
                new WxMpTemplateData("keyword2","￥"+orderDTO.getOrderAmount()),
                new WxMpTemplateData("keyword3",orderDTO.getCreateTime().toString()),
                new WxMpTemplateData("keyword4",orderDTO.getOrderId()),
                new WxMpTemplateData("remark","欢迎再次光临!")
        );
        templateMessage.setData(data);//设置推送的消息数据

        try{
            wxMpService.getTemplateMsgService().sendTemplateMsg(templateMessage);
        }catch(WxErrorException e){
            log.error("[微信模板消息] 发送失败，{}",e);

        }


    }
}
