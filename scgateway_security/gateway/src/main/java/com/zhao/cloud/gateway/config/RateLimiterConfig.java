package com.zhao.cloud.gateway.config;

import com.zhao.cloud.gateway.limiter.RemoteAddrKeyResolver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 限流过滤相关的配置
 *
 * @author zhaoliang
 */
@Configuration
public class RateLimiterConfig {
    @Bean(name = RemoteAddrKeyResolver.BEAN_NAME)
    public RemoteAddrKeyResolver remoteAddrKeyResolver() {
        return new RemoteAddrKeyResolver();
    }
}
