package com.zhao.cloud.gateway.security;

import com.alibaba.fastjson.JSONObject;
import com.zhao.cloud.gateway.common.domain.Result;
import com.zhao.cloud.gateway.enums.GatewayResultCode;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;

/**
 * 授权检验接口的默认实现
 * 首先，查看白名单配置项，如果匹配直接放行
 * 其次，根据配置项(checkTokenServiceName、checkTokenEndpointUrl)，请求鉴权中心进行权限鉴定
 *
 * @author zhaoliang
 */
public class DefaultAuthChecker implements AuthChecker {
    private final Log logger = LogFactory.getLog(getClass());

    private static final String AUTHORIZATION_HEADER = "Authorization";

    private LoadBalancerClient loadBalancerClient;
    private RestTemplate restTemplate;
    private AuthorizationProperty authorizationProperty;
    private AntPathMatcher antPathMatcher = new AntPathMatcher();

    public DefaultAuthChecker(LoadBalancerClient loadBalancerClient, RestTemplate restTemplate, AuthorizationProperty authorizationProperty) {
        this.loadBalancerClient = loadBalancerClient;
        this.restTemplate = restTemplate;
        this.authorizationProperty = authorizationProperty;
    }

    @Override
    public Result check(ServerHttpRequest serverHttpRequest) {
        if (needCheckAuth(serverHttpRequest)) {
            // 需要鉴权的请求
            String token = serverHttpRequest.getHeaders().getFirst(AUTHORIZATION_HEADER);
            if (StringUtils.isBlank(token)) {
                // 没有token，拦截请求
                return Result.failed(GatewayResultCode.UNAUTHORIZED.getCode(), GatewayResultCode.UNAUTHORIZED.getMessage());
            } else {
                HttpHeaders headers = new HttpHeaders();
                headers.setContentType(MediaType.APPLICATION_JSON);
                JSONObject authJsonObject = new JSONObject();
                authJsonObject.put("token", token);
                authJsonObject.put("resourceName", serverHttpRequest.getURI().getPath());
                HttpEntity<String> request = new HttpEntity<>(authJsonObject.toJSONString(), headers);

                String serviceUri = StringUtils.isNotBlank(authorizationProperty.getCheckTokenServiceName()) ?
                        loadBalancerClient.choose(authorizationProperty.getCheckTokenServiceName()).getUri().toString() : "";

                try {
                    Result authResult = restTemplate.postForObject(serviceUri + authorizationProperty.getCheckTokenEndpointUrl(), request, Result.class);
                    if (authResult == null || authResult.isFailed()) {
                        // 权限不足
                        return Result.failed(GatewayResultCode.FORBIDDEN.getCode(), GatewayResultCode.FORBIDDEN.getMessage());
                    }
                    return authResult;
                } catch (RestClientException e) {
                    logger.error("网关对用户的资源访问权限进行校验时，鉴权服务发生错误", e);
                    return Result.failed();
                }
            }
        }
        return Result.success();
    }

    private boolean needCheckAuth(ServerHttpRequest serverHttpRequest) {
        return authorizationProperty.getWhitelist().stream().noneMatch(pattern -> antPathMatcher.match(pattern ,serverHttpRequest.getURI().getPath()));
    }
}
