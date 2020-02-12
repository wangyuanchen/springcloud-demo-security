package com.zhao.cloud.gateway.auth.extend.filter;

import com.zhao.cloud.gateway.auth.extend.token.CompanyAuthenticationToken;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * 企业信息登陆过滤器
 *
 * @author zhaoliang
 */
public class CompanyLoginAuthenticationFilter extends AbstractAuthenticationProcessingFilter {
    private static final String TAX_NO_KEY = "taxNo";
    private static final String MACHINE_NO_STR_KEY = "machineNoStr";
    private static final String SPRING_SECURITY_RESTFUL_LOGIN_URL = "/companyLogin";
    private static final String SPRING_POST_METHOD = "POST";

    public CompanyLoginAuthenticationFilter() {
        super(new AntPathRequestMatcher(SPRING_SECURITY_RESTFUL_LOGIN_URL, SPRING_POST_METHOD));
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        if (!request.getMethod().equals(SPRING_POST_METHOD)) {
            throw new AuthenticationServiceException( "Authentication method not supported: " + request.getMethod());
        }

        String principal = obtainParameter(request, TAX_NO_KEY);
        String credentials = obtainParameter(request, MACHINE_NO_STR_KEY);
        AbstractAuthenticationToken authRequest = new CompanyAuthenticationToken(principal, credentials);
        // 记录其他请求信息
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    private void setDetails(HttpServletRequest request,
                            AbstractAuthenticationToken authRequest) {
        authRequest.setDetails(authenticationDetailsSource.buildDetails(request));
    }

    private String obtainParameter(HttpServletRequest request, String parameter) {
        return request.getParameter(parameter);
    }
}
