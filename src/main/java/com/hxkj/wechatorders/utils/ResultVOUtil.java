package com.hxkj.wechatorders.utils;

import com.hxkj.wechatorders.VO.ResultVO;

/** 返回给前端的自定义数据类型
 * Created by wangbin
 * 2018-07-11 1:46
 */
public class ResultVOUtil {

    public static ResultVO success(Object object){
        ResultVO resultVO = new ResultVO();
        resultVO.setData(object);
        resultVO.setMessage("成功");
        resultVO.setCode(0);
        return resultVO;
    }

    public static ResultVO success(){
        return  success(null);
    }

    public static ResultVO error(Integer code,String msg){
        ResultVO resultVO = new ResultVO();
        resultVO.setMessage(msg);
        resultVO.setCode(code);
        return resultVO;
    }
}
