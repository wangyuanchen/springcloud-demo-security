package com.zhao.cloud.gateway.goods;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

/**
 * 样例工程：商品
 * ComponentScan 指定高层级的包路径，能够扫描到common工程的组件
 *
 * @author zhaoliang
 */
@SpringBootApplication
@EnableDiscoveryClient
@ComponentScan(basePackages = "com.zhao.cloud.gateway")
public class GoodsApplication {
    public static void main(String[] args) {
        SpringApplication.run(GoodsApplication.class, args);
    }
}
