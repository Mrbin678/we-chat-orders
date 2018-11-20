package com.hxkj.wechatorders.service.impl;

import com.hxkj.wechatorders.converter.OrderMaster2OrderDTOConverter;
import com.hxkj.wechatorders.dataobject.OrderDetail;
import com.hxkj.wechatorders.dataobject.OrderMaster;
import com.hxkj.wechatorders.dataobject.ProductInfo;
import com.hxkj.wechatorders.dto.CartDTO;
import com.hxkj.wechatorders.dto.OrderDTO;
import com.hxkj.wechatorders.enums.OrderStatusEnum;
import com.hxkj.wechatorders.enums.PayStatusEnum;
import com.hxkj.wechatorders.enums.ResultEnum;
import com.hxkj.wechatorders.exception.SellException;
import com.hxkj.wechatorders.repository.OrderDetailRepository;
import com.hxkj.wechatorders.repository.OrderMasterRepository;
import com.hxkj.wechatorders.service.*;
import com.hxkj.wechatorders.utils.KeyUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.List;
import java.util.stream.Collectors;

/**
 * Created by wangbin
 * 2018-07-12 13:19
 */
@Service
@Slf4j
public class OrderServiceImpl implements OrderService{

    @Autowired
    private ProductService productService;
    @Autowired
    private OrderDetailRepository orderDetailRepository;
    @Autowired
    private OrderMasterRepository orderMasterRepository;
    @Autowired
    private PayService payServiceImpl;
    @Autowired
    private PushMessageService pushMessageServiceImpl;
    @Autowired
    private WebSocket webSocket;

    @Override
    @Transactional
    /**
     * 创建订单
     */
    public OrderDTO create(OrderDTO orderDTO) {
        //订单刚开始创建的时候就生成了订单主体的id
        String orderId = KeyUtil.genUniqueKey();
        //定义一个订单总金额,下面两种方式都可以
//      BigDecimal orderAmount = new BigDecimal(0);
        BigDecimal orderAmount = new BigDecimal(BigInteger.ZERO);

//        List<CartDTO> cartDTOList = new ArrayList<>();

        //service层不仅仅是直接调用dao层的方法，我们还要在这里进行各种逻辑的判断和数据的处理
        //创建订单的思考：前端到controller层，数据不可能都是从前端传过来，
        // 有些数据需要我们从数据库中取出来（比如：商品的价格肯定不可能从前端传过来），
        //有些数据需要我们计算好再往后端传（比如：计算订单的总金额）
        //1.创建订单（买家下单）先到数据库查询商品的数量、价格等信息
        for(OrderDetail orderDetail:orderDTO.getOrderDetailList()){
            ProductInfo productInfo = productService.findOne(orderDetail.getProductId());
            //判断商品是否存在
            if(productInfo == null){
                //如果不存在，抛出自定义异常（使用了枚举来代替普通常量的使用）
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //2.计算订单的总金额,不能用下面注释的方法，编译不会通过，
            // BigDecimal类型有特定的乘法计算方式,如下列未注释的代码,
            // 循环中先把每种的商品总金额计算出来然后加入到我们定义的orderAmount中（循环迭代），
            //最后赋值给orderAmount就是订单的总金额了（每算一种的商品总金额就add一次到orderAmount中）

//计算每个类型的商品总金额：
// orderDetail.getProductPrice().multiply(new BigDecimal(orderDetail.getProductQuantity()))

// orderDetail.getProductPrice()*orderDetail.getProductQuantity()  这个方式计算，编译通不过
            //这里有个坑，商品价格必须从数据库里获取，前台的orderDetail中不会传price，也不能传
            orderAmount = productInfo.getProductPrice()
                    .multiply(new BigDecimal(orderDetail.getProductQuantity()))
                    .add(orderAmount);

            //订单详情入库(由于前端传过来的数据有些字段是不会传过来的，例如:orderId,detailId,这些字段需要我们自己来生成)
            //通过自定义工具获取随机数（格式：时间+6位随机数）
            orderDetail.setDetailId(KeyUtil.genUniqueKey());
            orderDetail.setOrderId(orderId);
            //把productInfo的属性拷贝到orderDetail中，其中没对应上的属性不会赋值
            BeanUtils.copyProperties(productInfo,orderDetail);
            orderDetailRepository.save(orderDetail);

//            CartDTO cartDTO = new CartDTO(orderDetail.getProductId(),orderDetail.getProductQuantity());
//            cartDTOList.add(cartDTO);
        }
        //3.把处理好的数据调用dao层写入数据库（orderMaster,orderDetail），订单主表保存订单id和订单总金额
        OrderMaster orderMaster = new OrderMaster();
        //这里先把orderId设置到orderDTO再拷贝属性，不然会在创建订单的时候报错
        orderDTO.setOrderId(orderId);
        //属性拷贝，将orderDTO的属性拷贝到orderMaster中对应的属性中，没对应上的属性不会赋值
        BeanUtils.copyProperties(orderDTO,orderMaster);
        //这里有个坑，由于orderDTO中的orderID与OrderAmount是NULL，赋值的时候orderMaster的对应属性就会NULL，
        // 所以后面还需要设置这两个属性，不要先去设置这两个属性再拷贝，不然这两个属性会被覆盖为NULL，测试会报空指针异常
       //属性拷贝后订单orderStatus和payStatus也被覆盖为null了需要我们重新赋值
        orderMaster.setOrderAmount(orderAmount);
        //新订单
        orderMaster.setOrderStatus(OrderStatusEnum.NEW.getCode());
        //未支付
        orderMaster.setPayStatus(PayStatusEnum.WAIT.getCode());
        orderMasterRepository.save(orderMaster);


        //4.下单成功后需要扣库存
        // 使用java8的lambda表达式简化代码,不用到上面新建cartDTOList然后在循环中add，
        // 将orderDTO获取到的list转换为流然后获取其中的属性再设置到新的对象CartDTO中构造购物车对象，
        // 最后再收集成List集合
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList()
                .stream()
                .map(e -> new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());
        productService.decreaseStock(cartDTOList);
        //给前端发送websocket消息
        webSocket.sendMessage("有新的订单:"+orderDTO.getOrderId());
        return orderDTO;
    }

    @Override
    /**
     * 查询单个订单
     */
    public OrderDTO findOne(String orderId) {
        /*根据订单id获取订单主体信息*/
        OrderMaster orderMaster;
        try {
            orderMaster = orderMasterRepository.findById(orderId).get();
        }catch (Exception e){
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }

        /*如果订单主体为空，抛出自定义异常*/
        if (orderMaster == null) {
            throw new SellException(ResultEnum.ORDER_NOT_EXIST);
        }
        /*根据订单id获取订单详情信息集合*/
        List<OrderDetail> orderDetailList = orderDetailRepository.findByOrderId(orderId);
       /*如果订单详情为空，抛自定义异常*/
        if(CollectionUtils.isEmpty(orderDetailList)){
            throw new SellException(ResultEnum.ORDERDETAIL_NOT_EXIST);
        }
        OrderDTO orderDTO = new OrderDTO();
        /*拷贝orderMaster属性到orderDTO*/
        BeanUtils.copyProperties(orderMaster,orderDTO);
        orderDTO.setOrderDetailList(orderDetailList);
        return orderDTO;
    }

    @Override
    /**
     * 订单列表
     */
    public Page<OrderDTO> findList(String buyerOpenid, Pageable pageable) {
        /*这个方法中涉及到对象的装换，需要将Page<OrderMaster>装换成Page<OrderDTO>返回，
        对象转换使用到了自定义转换器 */
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findByBuyerOpenid(buyerOpenid,pageable);
        /*orderMasterPage.getContent()获取的是page对象中的集合*/
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        /*orderMasterPage.getTotalElements()获取的是page对象中集合的数目*/
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }

    @Override
    @Transactional
    /**
     * 取消订单
     */
    public OrderDTO cancel(OrderDTO orderDTO) {
        OrderMaster orderMaster = new OrderMaster();
        //判断订单状态,只有新下单的订单才可以被取消
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[取消订单] 订单状态不正确 orderId={},orderStatus={}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.CANCEL.getCode());
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult= orderMasterRepository.save(orderMaster);
       //判断订单更新结果
        if(updateResult == null){
           log.error("[取消订单] 订单更新失败 orderMaster={}",orderMaster);
           throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //返回库存
        if(CollectionUtils.isEmpty(orderDTO.getOrderDetailList())){
            log.error("[取消订单] 订单中无商品详情 orderDTO={}",orderDTO);
            throw new SellException(ResultEnum.ORDER_DETAIL_EMPTY);
        }
        List<CartDTO> cartDTOList = orderDTO.getOrderDetailList().stream()
                .map(e ->new CartDTO(e.getProductId(),e.getProductQuantity()))
                .collect(Collectors.toList());

        productService.increaseStock(cartDTOList);
        //如果用户已经支付，需要退款
        if(orderDTO.getPayStatus().equals(PayStatusEnum.SUCCESS.getCode())){
            payServiceImpl.refund(orderDTO);
        }
        return orderDTO;
    }

    @Override
    @Transactional
    /**
     * 完结订单
     */
    public OrderDTO finish(OrderDTO orderDTO) {

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[完结订单] 订单状态不正确 orderId = {},orderStatus = {}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //修改订单状态
        orderDTO.setOrderStatus(OrderStatusEnum.FINISHED.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[完结订单] 更新失败 orderMster = {}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        //推送微信模板消息
        pushMessageServiceImpl.orderStatus(orderDTO);
        return orderDTO;
    }

    @Override
    @Transactional
    /**
     * 支付订单
     */
    public OrderDTO Paid(OrderDTO orderDTO) {

        //判断订单状态
        if(!orderDTO.getOrderStatus().equals(OrderStatusEnum.NEW.getCode())){
            log.error("[订单支付成功] 订单状态不正确 orderId = {},orderStatus = {}",orderDTO.getOrderId(),orderDTO.getOrderStatus());
            throw new SellException(ResultEnum.ORDER_STATUS_ERROR);
        }
        //判断支付状态
        if(!orderDTO.getPayStatus().equals(PayStatusEnum.WAIT.getCode())){
            log.error("[订单支付成功] 订单支付状态不正确 orderDTO = {}",orderDTO);
            throw new SellException(ResultEnum.OREDER_PAY_SATUS_ERROR);
        }

        //修改支付状态
        orderDTO.setPayStatus(PayStatusEnum.SUCCESS.getCode());
        OrderMaster orderMaster = new OrderMaster();
        BeanUtils.copyProperties(orderDTO,orderMaster);
        OrderMaster updateResult = orderMasterRepository.save(orderMaster);
        if(updateResult == null){
            log.error("[订单支付成功] 更新失败 orderMster = {}",orderMaster);
            throw new SellException(ResultEnum.ORDER_UPDATE_FAIL);
        }
        return orderDTO;
    }

    @Override
    public Page<OrderDTO> findList(Pageable pageable) {
        Page<OrderMaster> orderMasterPage = orderMasterRepository.findAll(pageable);
        /*orderMasterPage.getContent()获取的是page对象中的集合*/
        List<OrderDTO> orderDTOList = OrderMaster2OrderDTOConverter.convert(orderMasterPage.getContent());
        /*orderMasterPage.getTotalElements()获取的是page对象中集合的数目*/
        return new PageImpl<OrderDTO>(orderDTOList,pageable,orderMasterPage.getTotalElements());
    }
}
