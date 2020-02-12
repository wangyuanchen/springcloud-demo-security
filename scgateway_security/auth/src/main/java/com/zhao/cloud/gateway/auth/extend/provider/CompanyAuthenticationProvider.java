package com.zhao.cloud.gateway.auth.extend.provider;

import com.zhao.cloud.gateway.auth.extend.token.CompanyAuthenticationToken;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

/**
 * 公司信息登陆验证器
 * @author zhaoliang
 */
public class CompanyAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        CompanyAuthenticationToken token = (CompanyAuthenticationToken)authentication;
        // 业务校验逻辑，例如：从DB中的Company表读取公司信息，如果状态正常则允许登陆
        UserDetails userDetails = new User(token.getPrincipal().toString(), token.getCredentials().toString(), new ArrayList<>());
        return new CompanyAuthenticationToken(userDetails);
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return CompanyAuthenticationToken.class.isAssignableFrom(authentication);
    }
}
