package com.zhao.cloud.gateway.filter;

import com.zhao.cloud.gateway.utils.GatewayLogUtil;
import com.zhao.cloud.gateway.utils.RecorderServerHttpRequestDecorator;
import com.zhao.cloud.gateway.utils.RecorderServerHttpResponseDecorator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * 低位日志过滤器，在filterChain中排位靠前
 * @author zhaoliang
 */
@Component
@ConditionalOnProperty(value = "gateway.log.enable")
public class LowerRequestRecorderGlobalFilter implements GlobalFilter, Ordered {
    private Logger logger = LoggerFactory.getLogger(LowerRequestRecorderGlobalFilter.class);

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        if (GatewayLogUtil.shouldSkip(exchange.getRequest())) {
            return chain.filter(exchange);
        }

        // 在 GatewayFilter 之前执行，此时的request时最初的request
        RecorderServerHttpRequestDecorator request = new RecorderServerHttpRequestDecorator(exchange.getRequest());

        // 此时的response是 发送回客户端的response
        RecorderServerHttpResponseDecorator response = new RecorderServerHttpResponseDecorator(exchange.getResponse());

        ServerWebExchange ex = exchange.mutate()
                .request(request)
                .response(response)
                .build();

        return GatewayLogUtil.recorderOriginalRequest(ex)
                .then(Mono.defer(() -> chain.filter(ex)))
                .then(Mono.defer(() -> finishLog(ex)));
    }

    private Mono<Void> finishLog(ServerWebExchange ex) {
        return GatewayLogUtil.recorderResponse(ex)
                .doOnSuccess(x -> logger.info(GatewayLogUtil.getLogData(ex) + "\n\n\n"));
    }

    @Override
    public int getOrder() {
        //在GatewayFilter之前执行
        return - 1;
    }
}
