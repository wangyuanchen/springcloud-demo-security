package com.zhao.cloud.gateway.limiter;

import lombok.Data;

import java.util.List;

/**
 * 限流相关配置项
 *
 * @author zhaoliang
 */
@Data
public class RateLimiterProperty {
    private Boolean enable;
    private int replenishRate;
    private int burstCapacity;
}
