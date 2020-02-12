package com.zhao.cloud.gateway.auth.controller;

import com.zhao.cloud.gateway.auth.dto.CheckAuthorizationReq;
import com.zhao.cloud.gateway.auth.enums.AuthResultCode;
import com.zhao.cloud.gateway.auth.utils.JwtTokenUtil;
import com.zhao.cloud.gateway.common.domain.Result;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 鉴权处理
 *
 * @author zhaoliang
 */
@RestController
@RequestMapping("/authorization")
public class AuthorizationController {
    private static final int TOKEN_PREFIX_LENGTH = 6;

    @PostMapping("/check")
    public Result<String> checkAuthorization(@RequestBody CheckAuthorizationReq param) {
        if (StringUtils.isNotBlank(param.getToken()) && JwtTokenUtil.validateToken(param.getToken().substring(TOKEN_PREFIX_LENGTH))) {
            if (StringUtils.isNotBlank(param.getResourceName())) {
                //TODO 判定请求者是否有权限访问资源
                return Result.success("1234444");
            } else {
                return Result.failed(AuthResultCode.FORBIDDEN.getCode(), AuthResultCode.FORBIDDEN.getMessage());
            }
        } else {
            return Result.failed(AuthResultCode.UNAUTHORIZED.getCode(), AuthResultCode.UNAUTHORIZED.getMessage());
        }
    }
}
