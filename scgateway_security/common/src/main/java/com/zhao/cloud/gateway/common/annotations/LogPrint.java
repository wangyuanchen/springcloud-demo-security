package com.zhao.cloud.gateway.common.annotations;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 打印日志注解
 *
 * @author zhaoliang
 */
@Target({ElementType.PARAMETER, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface LogPrint {
    // 是否打印参数，默认不打印
    boolean printParam() default false;
}