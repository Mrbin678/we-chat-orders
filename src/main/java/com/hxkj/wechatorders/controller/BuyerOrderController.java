package com.hxkj.wechatorders.controller;

import com.hxkj.wechatorders.VO.ResultVO;
import com.hxkj.wechatorders.converter.OrderForm2OrderDTOConverter;
import com.hxkj.wechatorders.dto.OrderDTO;
import com.hxkj.wechatorders.enums.ResultEnum;
import com.hxkj.wechatorders.exception.SellException;
import com.hxkj.wechatorders.form.OrderForm;
import com.hxkj.wechatorders.service.BuyerService;
import com.hxkj.wechatorders.service.OrderService;
import com.hxkj.wechatorders.utils.ResultVOUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by wangbin
 * 2018-07-17 13:51
 */
@RestController
@RequestMapping("buyer/order")
@Slf4j
//一个好的编码习惯就是将自己要做的事提前想好，然后再写接口和实现
public class BuyerOrderController {

    @Autowired
    private OrderService orderServiceImpl;
    @Autowired
    private BuyerService buyerServiceImpl;

    //创建订单
    @PostMapping("/create")
    public ResultVO<Map<String,String>> createOrder(@Valid OrderForm orderForm, BindingResult bindingResult){

        if(bindingResult.hasErrors()){
            log.error("[创建订单] 参数不正确 orderForm={}",orderForm);
            //抛自定义异常，这里新增了自定义异常的构造方法，通过返回结果枚举的状态码和验证表单返回的信息作为构造参数
            throw new SellException(ResultEnum.PARAM_ERROR.getCode(),
                    bindingResult.getFieldError().getDefaultMessage());
        }
        //使用自定义转换器将orderForm转换成我们的orderDTO
       OrderDTO orderDTO = OrderForm2OrderDTOConverter.convert(orderForm);
        //转换之后我们还是需要判断一下购物车是不是空的
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[创建订单] 购物车不能为空");
            throw new SellException(ResultEnum.CART_EMPTY);
        }
        OrderDTO result = orderServiceImpl.create(orderDTO);
        Map<String,String> map = new HashMap<>();
        map.put("orderId",result.getOrderId());
        return ResultVOUtil.success(map);
    }


    //订单列表
    @GetMapping("/list")
    public ResultVO<List<OrderDTO>> list(@RequestParam("openid") String openid,
                                         @RequestParam(value = "page" ,defaultValue = "0") Integer page,
                                         @RequestParam(value = "size" ,defaultValue = "10") Integer size){

        if(StringUtils.isEmpty(openid)){
            log.error("[订单查询列表] openid为空 ");
            throw new SellException(ResultEnum.PARAM_ERROR);
        }
        PageRequest request = PageRequest.of(page,size);
        Page<OrderDTO> orderDTOPage = orderServiceImpl.findList(openid,request);
        //返回前端的字段是非必须的字段如果为null可以通过全局配置过滤掉不返回给前端，
        // 如果返回前端的字段是必须的返回值为null，可以通过设置字段初始值的方式
        // 返回“”或[]等形式给前端（一般不返回null给前端，用“”或[]返回）
        return ResultVOUtil.success(orderDTOPage.getContent());
//        return ResultVOUtil.success();

    }


    //订单详情
    @GetMapping("/detail")
    public ResultVO<OrderDTO> detail(@RequestParam("openid") String openid,
                                     @RequestParam("orderId") String orderId){

        //这里我们把查询订单和取消订单的功能抽出来单独做了个接口（BuyerService）并在
        // 该实现类中通过openid和orderId这2个参数对订单是否属于本人进行了验证
        // 防止了越权访问，功能安全性已经得到保障
        OrderDTO orderDTO = buyerServiceImpl.findOrderOne(openid,orderId);
        return ResultVOUtil.success(orderDTO);
    }

    //取消订单
    @PostMapping("/cancel")
    public ResultVO cancel(@RequestParam("openid") String openid,
                           @RequestParam("orderId") String orderId){

        //取消订单之前先用orderId将订单的信息查出来用作取消订单之前的判断依据（判断是否可以取消）;
        buyerServiceImpl.cancelOrder(openid,orderId);
        return ResultVOUtil.success();
    }


}
