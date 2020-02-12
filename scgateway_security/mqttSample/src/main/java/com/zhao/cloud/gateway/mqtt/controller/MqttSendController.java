package com.zhao.cloud.gateway.mqtt.controller;

import com.zhao.cloud.gateway.mqtt.MqttSender;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/send")
public class MqttSendController {
    @Autowired
    private MqttSender mqttSender;

    @RequestMapping("/message")
    public String sendMqtt(String sendData) {
        mqttSender.sendToMqtt("hello", sendData);
        return "OK";

    }
}
