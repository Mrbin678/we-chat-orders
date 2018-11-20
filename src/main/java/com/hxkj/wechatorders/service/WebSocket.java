package com.hxkj.wechatorders.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.CopyOnWriteArraySet;

/**
 * Create by wangbin
 * 2018-10-31-14:36
 */
@Component
@ServerEndpoint("/webSocket")
@Slf4j
public class WebSocket {

    private Session session;
    //创建websocket容器
    private static CopyOnWriteArraySet<WebSocket> webSocketSet = new CopyOnWriteArraySet<>();
    @OnOpen
    public void onOpen(Session session){
        this.session = session;
        webSocketSet.add(this);
        log.info("[websocket消息] 有新的连接，总数：{}",webSocketSet.size());
    }
    @OnClose
    public void onClose(){
        webSocketSet.remove(this);
        log.info("[websocket消息] 连接断开，总数：{}",webSocketSet.size());
    }
    @OnMessage
    public void onMessage(String message){
        log.info("[websocket消息] 收到客户端发来的消息：{}",message);
    }
    public void sendMessage(String message){
        for (WebSocket webSocket:webSocketSet){
            log.info("[websocket消息] 广播消息，message={}",message);
           try{
               //发送消息
               webSocket.session.getBasicRemote().sendText(message);
           }catch(IOException e){
               e.printStackTrace();
           }

        }
    }
    /**
     * 方法说明：处理非正常关闭webSocket造成的报错
     * @author wangbin
     * @date 2018/11/2 10:00
     * @param e
     * @param session
     * @return void
     * @throws
     */
    @OnError
    public void onError(Throwable e,Session session){
        log.info("webSocket on Error handle");
    }
}
