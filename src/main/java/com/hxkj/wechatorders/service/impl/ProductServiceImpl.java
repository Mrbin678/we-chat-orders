package com.hxkj.wechatorders.service.impl;

import com.hxkj.wechatorders.dataobject.ProductInfo;
import com.hxkj.wechatorders.dataobject.dao.ProductCategoryDao;
import com.hxkj.wechatorders.dataobject.mapper.ProductCategoryMapper;
import com.hxkj.wechatorders.dto.CartDTO;
import com.hxkj.wechatorders.enums.ProductStatusEnum;
import com.hxkj.wechatorders.enums.ResultEnum;
import com.hxkj.wechatorders.exception.ResponseBankException;
import com.hxkj.wechatorders.exception.SellException;
import com.hxkj.wechatorders.repository.ProductInfoRepository;
import com.hxkj.wechatorders.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Created by wangbin
 * 2018-07-10 16:19
 */
@Service
//@CacheConfig(cacheNames = "product")
public class ProductServiceImpl implements ProductService {

    @Autowired //这里使用spring-data-jpa作为操作数据库的底层
    private ProductInfoRepository repository;
//    @Autowired  //使用mybatis作为操作数据库的底层，可以直接在service层注入mapper
//    private ProductCategoryMapper mapper;
//    @Autowired //使用mybatis不想直接注入mapper,可以先用一个dao层的类，然后在该类中注入mapper
//    private ProductCategoryDao dao;

    @Override
    @Transactional
    public void increaseStock(List<CartDTO> cartDTOList) {

        for(CartDTO cartDTO:cartDTOList){
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
            if(productInfo == null){
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            Integer result = productInfo.getProductStock() + cartDTO.getProductQuantity();
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }


    }

    @Override
    @Transactional
    public void decreaseStock(List<CartDTO> cartDTOList) {
        for(CartDTO cartDTO :cartDTOList){
            ProductInfo productInfo = repository.findById(cartDTO.getProductId()).get();
            if(productInfo == null){
                /*商品不存在*/
                throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
            }
            //每种商品的库存减去购物车中的商品数量
            Integer result = productInfo.getProductStock() - cartDTO.getProductQuantity();
            if(result < 0){
                /*商品库存不正确*/
                throw new SellException(ResultEnum.PRODUCT_STOCK_ERROR);
            }
            productInfo.setProductStock(result);
            repository.save(productInfo);
        }
    }

    @Override
    public ProductInfo onSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).get();
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum() == ProductStatusEnum.UP){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.UP.getCode());
        return repository.save(productInfo);
    }

    @Override
    public ProductInfo offSale(String productId) {
        ProductInfo productInfo = repository.findById(productId).get();
        if(productInfo == null){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
        }
        if(productInfo.getProductStatusEnum() == ProductStatusEnum.DOWN){
            throw new SellException(ResultEnum.PRODUCT_STATUS_ERROR);
        }
        //更新
        productInfo.setProductStatus(ProductStatusEnum.DOWN.getCode());
        return repository.save(productInfo);
    }

    @Override
//    @Cacheable(key="321")
    public ProductInfo findOne(String productId) {
        try{
            //这里查询商品的时候可能会出错需要进行异常处理
            return repository.findById(productId).get();
        }catch(Exception e){
            throw new SellException(ResultEnum.PRODUCT_NOT_EXIST);
//            throw new ResponseBankException();//此处假定给银行抛出一个异常
        }

    }

    @Override
    public List<ProductInfo> findUpAll() {
        //这里为了不在代码中直接写常量，我们定义了枚举类型来对商品的状态进行持久，从枚举中获取状态值作为参数
        return repository.findByProductStatus(ProductStatusEnum.UP.getCode());
    }

    @Override
    public Page<ProductInfo> findAll(Pageable pageable) {
        /*这里涉及到分页，传入pageable对象，返回Page对象的泛型*/
        return repository.findAll(pageable);
    }

    @Override
//    @CachePut(key="321")
    public ProductInfo save(ProductInfo productInfo) {
        return repository.save(productInfo);
    }




}
