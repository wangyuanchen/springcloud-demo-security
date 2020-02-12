package com.zhao.cloud.gateway.filter;

import com.zhao.cloud.gateway.utils.GatewayLogUtil;
import com.zhao.cloud.gateway.utils.RecorderServerHttpRequestDecorator;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 高位日志过滤器，在filterChain中排位靠后
 * @author zhaoliang
 */
@Component
@ConditionalOnProperty(value = "gateway.log.enable")
public class HigherRequestRecorderGlobalFilter implements GlobalFilter, Ordered {
    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (GatewayLogUtil.shouldSkip(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        //在 NettyRoutingFilter 之前执行， 基本上属于倒数第二个过滤器了
        //此时的request是 经过各种转换、转发之后的request
        //对应日志中的 代理请求 部分
        RecorderServerHttpRequestDecorator request = new RecorderServerHttpRequestDecorator(exchange.getRequest());
        ServerWebExchange ex = exchange.mutate()
                .request(request)
                .build();

        return GatewayLogUtil.recorderRouteRequest(ex)
                .then(Mono.defer(() -> chain.filter(ex)));
    }

    @Override
    public int getOrder() {
        //在向业务服务转发前执行  NettyRoutingFilter 或 WebClientHttpRoutingFilter
        return Ordered.LOWEST_PRECEDENCE - 10;
    }
}
