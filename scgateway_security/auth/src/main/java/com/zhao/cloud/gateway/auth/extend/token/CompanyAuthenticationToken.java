package com.zhao.cloud.gateway.auth.extend.token;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.userdetails.UserDetails;

/**
 * 企业信息登陆token
 *
 * @author zhaoliang
 */
public class CompanyAuthenticationToken extends AbstractAuthenticationToken {
    private static final long serialVersionUID = -8021351782132629598L;
    private String taxNo;
    private String machineNoStr;
    private UserDetails userDetails;

    public CompanyAuthenticationToken(String taxNo, String machineNoStr) {
        super(null);
        this.taxNo = taxNo;
        this.machineNoStr = machineNoStr;
    }

    public CompanyAuthenticationToken(UserDetails userDetails) {
        super(userDetails.getAuthorities());
        this.userDetails = userDetails;
        this.taxNo = userDetails.getPassword();
        this.machineNoStr = userDetails.getUsername();
    }

    @Override
    public Object getCredentials() {
        return taxNo;
    }

    @Override
    public Object getPrincipal() {
        return userDetails == null ? machineNoStr : userDetails;
    }
}
