package com.zhao.cloud.gateway.goods.controller;

import com.zhao.cloud.gateway.common.annotations.LogPrint;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * 商品服务 模拟被访问的资源服务
 * @author zhaoliang
 */
@RestController
public class GoodsInfoController {

    @LogPrint(printParam = true)
    @RequestMapping(value = "/infoByName", method = RequestMethod.POST)
    public Map<String, Object> getInfoByName(@RequestParam String goodsName){
        Map<String, Object> info = new HashMap<>(16);
        info.put("name", goodsName);
        info.put("price", 25);
        return info;
    }
}
