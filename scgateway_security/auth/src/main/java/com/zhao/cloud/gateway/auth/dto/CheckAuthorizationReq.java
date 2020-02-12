package com.zhao.cloud.gateway.auth.dto;

import lombok.Data;

@Data
public class CheckAuthorizationReq {
    private String token;
    private String resourceName;
}
