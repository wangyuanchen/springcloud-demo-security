package com.zhao.cloud.gateway.security;

import com.zhao.cloud.gateway.common.domain.Result;
import org.springframework.http.server.reactive.ServerHttpRequest;

/**
 * 授权检验接口
 *
 * @author zhaoliang
 */
public interface AuthChecker {
    /**
     * 验证token是否有效，且该token是否有访问resourceName的授权
     *
     * @param serverHttpRequest 服务调用请求
     * @return 验证结果
     */
    Result check(ServerHttpRequest serverHttpRequest);
}
