package com.hxkj.wechatorders.VO;

import lombok.Data;

import java.io.Serializable;

/** http请求返回的最外层对象
 * 返回的最外层信息中的内容成员变量是复杂类型，是哪种类型我们没定死，由此我们进行了泛型化,
 * 如果不进行泛型化，把成员变量定义成Object类型，那么每次就需要进行强制装换很不方便
 * Created by wangbin
 * 2018-07-10 23:26
 */
@Data
public class ResultVO<T> implements Serializable {
    //在idea中下载了SerialVersionUID插件，设置了快捷键shift+I，自动生成序列化ID(唯一的)
    private static final long serialVersionUID = 2698832862627941432L;
    /*状态码*/
    private Integer code;
    /*提示信息*/
    private String message;
    /*返回的具体内容，data其实是一个对象,我们定义为泛型*/
    private T data ;
}
