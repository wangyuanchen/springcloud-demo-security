package com.zhao.cloud.gateway.common.aspects;

import com.alibaba.fastjson.JSON;
import com.zhao.cloud.gateway.common.annotations.LogPrint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 打印日志切面
 *
 * @author zhaoliang
 */
@Aspect
@Component
public class LogPrintAspect {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    private static final String METHOD_PREFIX = "method=";
    private static final String PARAM_PREFIX = "，request params=";
    private static final String PARAM_SEPARATOR = ",";
    private static final String RESULT_PREFIX = "，response params=";


    @Around(value = "@annotation(logPrint)")
    public Object printLog(ProceedingJoinPoint joinPoint, LogPrint logPrint) throws Throwable {
        StringBuilder enterInfo = new StringBuilder(METHOD_PREFIX + joinPoint.getSignature().getName());
        if (logPrint.printParam()) {
            enterInfo.append(PARAM_PREFIX);
            for (Object object : joinPoint.getArgs()) {
                if (object instanceof MultipartFile
                        || object instanceof HttpServletRequest
                        || object instanceof HttpServletResponse) {
                    continue;
                }
                enterInfo.append(JSON.toJSONString(object)).append(PARAM_SEPARATOR);
            }
        }
        logger.info(enterInfo.toString());
        final Object proceed = joinPoint.proceed();
        StringBuilder exitInfo = new StringBuilder(METHOD_PREFIX + joinPoint.getSignature().getName());
        if (logPrint.printParam()) {
            exitInfo.append(RESULT_PREFIX).append(JSON.toJSONString(proceed));
        }
        logger.info(exitInfo.toString());
        return proceed;
    }

}
