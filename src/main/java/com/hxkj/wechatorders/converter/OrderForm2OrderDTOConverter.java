package com.hxkj.wechatorders.converter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.hxkj.wechatorders.dataobject.OrderDetail;
import com.hxkj.wechatorders.dto.OrderDTO;
import com.hxkj.wechatorders.enums.ResultEnum;
import com.hxkj.wechatorders.exception.SellException;
import com.hxkj.wechatorders.form.OrderForm;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**OrderForm转换成OrderDTO的转换器
 * Created by wangbin
 * 2018-07-17 14:29
 */
@Slf4j
public class OrderForm2OrderDTOConverter {

    public static OrderDTO convert(OrderForm orderForm){
        //com.google.code.gson的pom依赖，用于转换json
        Gson gson = new Gson();

        OrderDTO orderDTO = new OrderDTO();
        orderDTO.setBuyerName(orderForm.getName());
        orderDTO.setBuyerPhone(orderForm.getPhone());
        orderDTO.setBuyerAddress(orderForm.getAddress());
        orderDTO.setBuyerOpenid(orderForm.getOpenid());

        List<OrderDetail> orderDetailList = new ArrayList<>();
        try{
            //这里将前端传过来的购物车json字符装换成我们所需要的参数List<OrderDetail>,
            // json转换的方法中填入需要转换的内容和需要转换成的格式
            // 由于前端可能不会传Json格式的字符，这里可能装换出错，需要捕获异常并抛出自定义异常
            orderDetailList = gson.fromJson(orderForm.getItems(),new TypeToken<List<OrderDetail>>(){}.getType());
        }catch (Exception e){
            log.error("[对象转换] 错误，json={}",orderForm.getItems());
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        //这里尤为注意，购物车传过来的是字符，是json格式，这里需要将json转换为List<OrderDetail>(用到了谷歌的一个转换json的pom依赖)
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;

        //由于两个对象的字段名不同，这里不能通过拷贝属性的方式来转换对象
//        BeanUtils.copyProperties(orderForm,orderDTO);
    }

}
