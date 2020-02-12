package com.zhao.cloud.gateway.common.domain;

/**
 * 公共状态码
 *  @author zhaoliang
 */
public enum CommonResultCode {
    // 请求成功:成功统一使用此状态
    SUCCESS("000000","请求成功"),
    // 系统底层异常:各个业务不要使用此错误码，各个业务应该对自己的错误进行详细描述，仅有框架级错误返回此code
    SYSTEM_ERROR("000001", "系统异常"),
    ;

    private String code;
    private String message;

    CommonResultCode(String code, String message) {
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
