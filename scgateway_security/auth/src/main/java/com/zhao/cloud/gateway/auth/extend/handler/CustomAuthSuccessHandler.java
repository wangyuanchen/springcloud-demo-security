package com.zhao.cloud.gateway.auth.extend.handler;

import com.alibaba.fastjson.JSONObject;
import com.zhao.cloud.gateway.auth.utils.JwtTokenUtil;
import com.zhao.cloud.gateway.common.domain.Result;
import org.springframework.http.MediaType;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SavedRequestAwareAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * 登陆成功之后的处理，生成token
 *
 * @author zhaoliang
 */
@Component
public class CustomAuthSuccessHandler extends SavedRequestAwareAuthenticationSuccessHandler {
    private static final String TOKEN_BODY_KEY = "token";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private static final String TOKEN_HEADER_PREFIX = "bearer ";

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException {
        String token = JwtTokenUtil.generateToken((UserDetails) authentication.getPrincipal());
        response.setContentType(MediaType.APPLICATION_JSON_UTF8.toString());
        Map<String, Object> body = new HashMap<>(16);
        body.put(TOKEN_BODY_KEY, token);
        response.getWriter().write(JSONObject.toJSONString(Result.success(body)));
        response.addHeader(AUTHORIZATION_HEADER, TOKEN_HEADER_PREFIX + token);
    }
}