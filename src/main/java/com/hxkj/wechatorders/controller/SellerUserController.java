package com.hxkj.wechatorders.controller;

import com.hxkj.wechatorders.config.ProjectUrlConfig;
import com.hxkj.wechatorders.constant.CookieConstant;
import com.hxkj.wechatorders.constant.RedisConstant;
import com.hxkj.wechatorders.dataobject.SellerInfo;
import com.hxkj.wechatorders.enums.ResultEnum;
import com.hxkj.wechatorders.service.SellerService;
import com.hxkj.wechatorders.utils.CookieUtil;
import com.hxkj.wechatorders.utils.RedisUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.TimeUnit;

/**卖家用户相关控制层
 * Create by wangbin
 * 2018-10-25-22:18
 */
@Controller
@RequestMapping("/seller")
public class SellerUserController {

    @Autowired
    private SellerService sellerServiceImpl;
    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private ProjectUrlConfig projectUrlConfig;


    /**
     * 方法说明：用户登陆，数据库查询匹配设备的openid,设置token到redis，设置token到cookie
     * @author wangbin
     * @date 2018/10/30 9:07
     * @param openid
     * @param response
     * @param map
     * @return org.springframework.web.servlet.ModelAndView
     * @throws
     */
    @GetMapping("/login")
    public ModelAndView login(@RequestParam("openid") String openid,
                              HttpServletResponse response,
                              Map<String,Object> map){

        //1.openid与数据库中的数据进行匹配
        SellerInfo sellerInfo = sellerServiceImpl.findSellerInfoByOpenid(openid);
        if(sellerInfo == null){
            map.put("msg", ResultEnum.LOGIN_FAIL.getMsg());
            //本来这里查不到卖家信息会跳到订单列表页，但是由于做了切面会跳到权限不够提示页，然后跳到一个网址
            map.put("url","/sell/seller/order/list");//这里本来是跳转到微信商户平台的回调地址，即扫码登陆的地址，由于没有企业版的微信开放平台，这里随便设置了一个跳转的网址
            return new ModelAndView("common/error",map);
        }
        
        //2.设置token至redis
        //操作redis数据库，往里面写入值(redis的写入)
        //redisTemplate.opsForValue().set("abc","123456");
        //注意使用redis做存储信息的时候一定要设置过期时间，
        // 因为redis是用来做缓存的，数据需要定期清除

        //生成一个UUID来作为token
        String token = UUID.randomUUID().toString();
        //设置过期时间
        Integer expire = RedisConstant.EXPIRE;
        //向redis数据库写入值，格式化了一下token，将key设置为以“token_”为前缀的字符串，
        // openid作为value,设置了数据存入redis的过期时间，以及时间的格式以秒为单位
        redisTemplate.opsForValue().set(String.format(RedisConstant.TOKEN_PREFIX,token),openid,expire, TimeUnit.SECONDS);
        //3.设置token至cookie
        //设置cookie(注意要设置过期时间)
        CookieUtil.set(response,CookieConstant.TOKEN,token,expire);

        //用户登陆过程：现根据用户扫码后获取到的openid到数据库查一下有没有，
        //有的话就生成UUID带上特定的前缀作为key,openid作为value放入redis数据库
        //一定要要记得设置过期时间，然后把再把token设置到cookie中

        //用户登陆校验过程：先从浏览器中的cookie根据token这个name取到value值，
        //取到这个值之后再去redis根据这个值查一下有没有对应的openid这个值，
        // 查到了就说明用户登陆了，没查到就说明用户未登录，需要进行相应操作，
        // 比如提示用户未登录然后跳回登陆页面
        //重定向跳转最好用完整的地址，减少相对路径的跳转
        return new ModelAndView("redirect:"+projectUrlConfig.getSell()+"/sell/seller/order/list");
    }

    /**
     * 方法说明：用户登出，清除redis,清除cookie
     * @author wangbin
     * @date 2018/10/30 9:06
     * @param request
     * @param response
     * @param map
     * @return void
     * @throws
     */
    @GetMapping("/logout")
    public ModelAndView logout(HttpServletRequest request,
                       HttpServletResponse response,
                       Map<String,Object> map){
        //1.从cookie查询
        Cookie cookie = CookieUtil.get(request,CookieConstant.TOKEN);
        if(cookie !=null){
            //2.清除redis(根据cookie中获取的UUID加上一个token前缀格式化为redis的key,根据key删除对应的value)
            redisTemplate.opsForValue().getOperations().delete(String.format(RedisConstant.TOKEN_PREFIX,cookie.getValue()));
            //3.清除cookie(设置cookie值为null，过期时间为0)
            CookieUtil.set(response,CookieConstant.TOKEN,null,0);
        }
        map.put("msg",ResultEnum.LOGOUT_SUCCESS.getMsg());
        //本来这里登出会跳到订单列表吧=页，由于做了AOP切面，登出后没有了cookie会跳到权限不够提示页然后跳到一个网址
        //由于没有微信开放平台企业级资质，没法设置扫码登陆的回调页面，只好先让其跳到权限不够页面然后跳到一个网址
        map.put("url","/sell/seller/order/list");
        return new ModelAndView("common/success",map);
    }
}
