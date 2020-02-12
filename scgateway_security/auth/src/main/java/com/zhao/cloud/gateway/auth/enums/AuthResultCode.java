package com.zhao.cloud.gateway.auth.enums;

/**
 * 鉴权服务状态码
 *  @author zhaoliang
 */
public enum AuthResultCode {
    // 操作未授权：没有令牌
    UNAUTHORIZED("200000", "操作未授权"),
    // 禁止的操作：当前令牌不能访问目标资源
    FORBIDDEN("200001","禁止的操作"),
    // 认证失败：用户名、密码不匹配等
    AUTHENTICATION_FAILED("200002","认证失败"),
    ;

    private String code;
    private String message;

    AuthResultCode(String code, String message) {
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
