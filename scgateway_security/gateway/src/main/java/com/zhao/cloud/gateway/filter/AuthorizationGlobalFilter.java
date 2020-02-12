package com.zhao.cloud.gateway.filter;

import com.alibaba.fastjson.JSONObject;
import com.zhao.cloud.gateway.common.domain.Result;
import com.zhao.cloud.gateway.enums.GatewayResultCode;
import com.zhao.cloud.gateway.security.AuthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;

/**
 * 鉴权过滤器，验证访问者是否有权限。
 * 有权限，则放行；无权限，则拦截
 *
 * @author zhaoliang
 */
public class AuthorizationGlobalFilter implements GlobalFilter, Ordered {
    private static final String USER_INFO_HEADER = "X-USER-INFO";

    @Autowired
    private AuthChecker authChecker;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        Result authResult = authChecker.check(exchange.getRequest());
        if (authResult.isFailed()) {
            // 权限不足
            Result authFailed = Result.failed(GatewayResultCode.FORBIDDEN.getCode(), GatewayResultCode.FORBIDDEN.getMessage());
            // 此处仍然返回HttpStatus.OK，可根据前端约定考虑是否返回FORBIDDEN
            return writeResponse(exchange, authFailed, HttpStatus.OK);
        } else {
            // 权限验证通过，增强请求头信息
            exchange.getRequest().mutate().header(USER_INFO_HEADER, JSONObject.toJSONString(authResult.getData())).build();
        }
        return chain.filter(exchange);
    }

    private Mono<Void> writeResponse(ServerWebExchange exchange, Result authFailed, HttpStatus returnHttpStatus) {
        ServerHttpResponse response = exchange.getResponse();
        DataBuffer buffer = response.bufferFactory().wrap(JSONObject.toJSONString(authFailed).getBytes(StandardCharsets.UTF_8));
        response.setStatusCode(returnHttpStatus);
        // 指定编码，防止中文乱码
        response.getHeaders().setContentType(MediaType.APPLICATION_JSON_UTF8);
        return response.writeWith(Mono.just(buffer));
    }

    @Override
    public int getOrder() {
        //在向业务服务转发前执行  NettyRoutingFilter 或 WebClientHttpRoutingFilter
        return -2;
    }
}
