package com.zhao.cloud.gateway.enums;

/**
 * 鉴权服务状态码
 *  @author zhaoliang
 */
public enum GatewayResultCode {
    // 操作未授权:没有授权凭证
    UNAUTHORIZED("100000", "操作未授权"),
    // 禁止的操作:授权凭证的权限不足，无法访问资源
    FORBIDDEN("100001","禁止的操作"),
    ;

    private String code;
    private String message;

    GatewayResultCode(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public String getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }
}
