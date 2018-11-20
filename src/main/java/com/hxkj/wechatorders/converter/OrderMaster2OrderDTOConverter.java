package com.hxkj.wechatorders.converter;

import com.hxkj.wechatorders.dataobject.OrderMaster;
import com.hxkj.wechatorders.dto.OrderDTO;
import org.springframework.beans.BeanUtils;

import java.util.List;
import java.util.stream.Collectors;

/**转换器：将orderMaster转换成orderDTO
 * Created by wangbin
 * 2018-07-16 14:40
 */
public class OrderMaster2OrderDTOConverter {

    public static OrderDTO convert(OrderMaster orderMaster){
        OrderDTO orderDTO = new OrderDTO();
        BeanUtils.copyProperties(orderMaster,orderDTO);
        return orderDTO;
    }

    public static List<OrderDTO> convert(List<OrderMaster> orderMasterList){
        /*这里采用lambda表达式，调用了上面单个转换orderMaster的convert方法
        将集合中的orderMaster装换为orderDTO,然后收集成一个OrderDTO的List集合返回
        * */
        return  orderMasterList.stream().map(e ->convert(e)).collect(Collectors.toList());
    }

}
