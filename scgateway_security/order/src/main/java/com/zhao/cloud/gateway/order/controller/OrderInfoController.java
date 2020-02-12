package com.zhao.cloud.gateway.order.controller;

import com.zhao.cloud.gateway.order.feign.GoodsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * 订单服务 模拟请求访问资源的服务
 *
 * @author zhaoliang
 */
@RestController
public class OrderInfoController {

    @Autowired
    private GoodsService goodsService;

    @RequestMapping(value = "/infoByName", method = RequestMethod.POST)
    public Map<String, Object> getInfoByName(@RequestParam String orderNo) {
        Map<String, Object> info = new HashMap<>(16);
        info.put("orderNo", orderNo);
        info.put("goodsInfo", goodsService.getInfoByName("apple"));
        return info;
    }
}
