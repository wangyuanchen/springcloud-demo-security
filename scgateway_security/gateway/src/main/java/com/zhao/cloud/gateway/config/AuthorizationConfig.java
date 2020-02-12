package com.zhao.cloud.gateway.config;

import com.zhao.cloud.gateway.filter.AuthorizationGlobalFilter;
import com.zhao.cloud.gateway.security.AuthorizationProperty;
import com.zhao.cloud.gateway.security.DefaultAuthChecker;
import com.zhao.cloud.gateway.security.AuthChecker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.loadbalancer.LoadBalancerClient;
import org.springframework.cloud.netflix.ribbon.RibbonClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * 鉴权过滤相关的配置
 * 鉴权过滤的主入口是AuthorizationGlobalFilter
 * 真正的鉴权逻辑是委托给auth服务的（在TokenChecker中调用），
 * 所以，还需要LoadBalancerClient和RestTemplate
 *
 * @author zhaoliang
 */
@Configuration
@EnableConfigurationProperties
@RibbonClient(name = "${gateway.authorization.checkTokenServiceName}")
@ConditionalOnProperty(value = "gateway.authorization.enable")
public class AuthorizationConfig {

    @Autowired
    private LoadBalancerClient loadBalancerClient;

    @Bean
    public RestTemplate restTemplate() {
        return new RestTemplate();
    }

    @Bean
    @ConfigurationProperties(prefix = "gateway.authorization")
    public AuthorizationProperty authorizationProperty() {
        return new AuthorizationProperty();
    }

    @Bean
    public AuthChecker tokenChecker(RestTemplate restTemplate, AuthorizationProperty authorizationProperty) {
        return new DefaultAuthChecker(loadBalancerClient, restTemplate, authorizationProperty);
    }

    @Bean
    public AuthorizationGlobalFilter authorizationGlobalFilter() {
        return new AuthorizationGlobalFilter();
    }

}
