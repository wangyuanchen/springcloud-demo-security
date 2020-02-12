package com.zhao.cloud.gateway.limiter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cloud.gateway.filter.ratelimit.KeyResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 限流key生成器
 *
 * @author zhaoliang
 */
public class RemoteAddrKeyResolver implements KeyResolver {
    private static final Logger LOGGER = LoggerFactory.getLogger(RemoteAddrKeyResolver.class);

    public static final String BEAN_NAME = "remoteAddrKeyResolver";

    @Override
    public Mono<String> resolve(ServerWebExchange exchange) {
        LOGGER.debug("token limit for ip: {} ", exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
        return Mono.just(exchange.getRequest().getRemoteAddress().getAddress().getHostAddress());
    }

}
