package com.zhao.cloud.gateway.socket.controller;

import com.zhao.cloud.gateway.socket.dto.BulletMessageVO;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

@Controller
public class BulletController {
    @MessageMapping("/chat")
    //SendTo 发送至 Broker 下的指定订阅路径
    @SendTo("/toAll/bulletScreen")
    public String say(BulletMessageVO clientMessage) {
        String result=null;
        if (clientMessage!=null){
            result=clientMessage.getUsername()+":"+clientMessage.getMessage();
        }
        return result;
    }
}