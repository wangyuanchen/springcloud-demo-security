package com.zhao.cloud.gateway.security;

import lombok.Data;

import java.util.List;

/**
 * 鉴权相关配置项
 *
 * @author zhaoliang
 */
@Data
public class AuthorizationProperty {
    private Boolean enable;
    private String checkTokenServiceName;
    private String checkTokenEndpointUrl;
    private List<String> whitelist;
}
