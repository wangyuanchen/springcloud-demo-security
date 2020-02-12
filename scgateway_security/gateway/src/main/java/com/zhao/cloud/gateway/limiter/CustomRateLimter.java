package com.zhao.cloud.gateway.limiter;

import org.springframework.cloud.gateway.filter.ratelimit.AbstractRateLimiter;
import org.springframework.cloud.gateway.filter.ratelimit.RateLimiter;
import reactor.core.publisher.Mono;

import java.util.Map;

/**
 * @author: wangyuanchen
 * @date: 2020-1-10 14:03
 * @description:
 */
public class CustomRateLimter extends AbstractRateLimiter {
    @Override
    public Mono<Response> isAllowed(String routeId, String id) {
        return null;
    }

    @Override
    public Map getConfig() {
        return null;
    }

    @Override
    public Class getConfigClass() {
        return null;
    }

    @Override
    public Object newConfig() {
        return null;
    }
}
