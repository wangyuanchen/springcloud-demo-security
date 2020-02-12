package com.zhao.cloud.gateway.order.feign;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.Map;

/**
 * 样例服务：商品
 *
 * @author zhaoliang
 */
@FeignClient(name = "goods")
public interface GoodsService {
    /**
     * 样例接口：根据商品名称取得商品信息
     *
     * @param goodsName 商品名
     * @return 商品信息
     */
    @RequestMapping(value = "/infoByName", method = RequestMethod.POST)
    Map<String, Object> getInfoByName(@RequestParam String goodsName);
}
