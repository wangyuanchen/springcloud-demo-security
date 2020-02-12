package com.zhao.cloud.gateway.auth.extend.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhao.cloud.gateway.auth.enums.AuthResultCode;
import com.zhao.cloud.gateway.common.domain.Result;
import org.springframework.http.MediaType;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * 登陆失败之后的处理，返回错误信息
 *
 * @author zhaoliang
 */
@Component
public class CustomAuthenticationFailureHandler implements AuthenticationFailureHandler {
    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response,
                                        AuthenticationException exception) throws IOException {
        response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
        response.getWriter().write(JSONObject.toJSONString(Result.failed(AuthResultCode.AUTHENTICATION_FAILED.getCode(),
                AuthResultCode.AUTHENTICATION_FAILED.getMessage())));
    }
}
