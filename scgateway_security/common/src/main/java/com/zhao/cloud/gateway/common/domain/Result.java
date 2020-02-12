package com.zhao.cloud.gateway.common.domain;

import com.alibaba.fastjson.annotation.JSONField;

import java.io.Serializable;

/**
 * 通用数据序列化传递对象
 *
 * @author zhaoliang
 */
public class Result<T> implements Serializable {
    private static final long serialVersionUID = -5182355327536264743L;

    private String code;
    private String message;
    private T data;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> Result<T> success() {
        Result<T> result = new Result<>();
        result.setCode(CommonResultCode.SUCCESS.getCode());
        result.setMessage(CommonResultCode.SUCCESS.getMessage());
        return result;
    }

    public static <T> Result<T> success(String message, T data) {
        Result<T> result = new Result<>();
        result.setCode(CommonResultCode.SUCCESS.getCode());
        result.setMessage(message);
        result.setData(data);
        return result;
    }

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.setCode(CommonResultCode.SUCCESS.getCode());
        result.setMessage(CommonResultCode.SUCCESS.getMessage());
        result.setData(data);
        return result;
    }

    public static <T> Result<T> failed() {
        Result<T> result = new Result<>();
        result.setCode(CommonResultCode.SYSTEM_ERROR.getCode());
        result.setMessage(CommonResultCode.SYSTEM_ERROR.getMessage());
        return result;
    }

    public static <T> Result<T> failed(String code, String message) {
        Result<T> result = new Result<>();
        result.setCode(code);
        result.setMessage(message);
        return result;
    }

    @JSONField(serialize = false)
    public boolean isSuccess() {
        return CommonResultCode.SUCCESS.getCode().equals(code);
    }

    @JSONField(serialize = false)
    public boolean isFailed() {
        return !isSuccess();
    }

}
