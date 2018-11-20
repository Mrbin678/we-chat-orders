package com.hxkj.wechatorders.handler;

import com.hxkj.wechatorders.VO.ResultVO;
import com.hxkj.wechatorders.config.ProjectUrlConfig;
import com.hxkj.wechatorders.enums.ResultEnum;
import com.hxkj.wechatorders.exception.ResponseBankException;
import com.hxkj.wechatorders.exception.SellException;
import com.hxkj.wechatorders.exception.SellerAuthorizeException;
import com.hxkj.wechatorders.utils.ResultVOUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;

import java.util.HashMap;
import java.util.Map;

/** 异常处理器
 * Create by wangbin
 * 2018-10-30-15:45
 */
@ControllerAdvice //@ControllerAdvice 注解，用于拦截全局的Controller的异常，注意：ControllerAdvice注解只拦截Controller不会拦截Interceptor的异常
public class SellExceptionHandler {

    @Autowired
    private ProjectUrlConfig projectUrlConfig;

    //拦截登录异常
    //http://binbin678.natapp1.cc/sell/wechat/qrAuthorize?returnUrl=http://binbin678.natapp1.cc/sell/seller/login
    @ExceptionHandler(value = SellerAuthorizeException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)//捕获异常后，通过这个注解和填入的枚举值返回给前端的信息中状态码为403
    public ModelAndView handlerAuthorizeException(){
//        return new ModelAndView("redirect:"
//        .concat(projectUrlConfig.getWechatOpenAuthorize())
//        .concat("/sell/wechat/qrAuthorize")
//        .concat("?returnUrl=")
//        .concat(projectUrlConfig.getSell())
//        .concat("/sell/seller/login"));

        //由于没有微信开放平台的企业账号，这里无法用回调的url，只能自行处理提示信息和跳转地址
        //这里跳转到无权限提示页面，然后跳到慕课网网址
        return  new ModelAndView("common/noPermission");
    }

    @ExceptionHandler(value = SellException.class)
    @ResponseBody
    @ResponseStatus(HttpStatus.FORBIDDEN)//此处注解加上HttpStatus.FORBIDDEN枚举值，返回的http状态码为403
    public ResultVO handlerSellException(SellException e){
        return ResultVOUtil.error(e.getCode(),e.getMessage());
    }

    @ExceptionHandler(value = ResponseBankException.class)
    @ResponseStatus(HttpStatus.FORBIDDEN)
    //捕获返回给银行的一个异常，返回403（假定的测试）
    public void handlerResponseBankException(){

    }

}
