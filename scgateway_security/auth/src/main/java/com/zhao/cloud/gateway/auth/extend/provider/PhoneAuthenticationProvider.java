package com.zhao.cloud.gateway.auth.extend.provider;

import com.zhao.cloud.gateway.auth.extend.token.PhoneAuthenticationToken;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.ArrayList;

/**
 * 手机验证码登陆验证器
 * @author zhaoliang
 */
public class PhoneAuthenticationProvider extends AbstractUserAuthenticationProvider {
    @Override
    protected void additionalAuthenticationChecks(UserDetails userDetails, Authentication authentication) throws AuthenticationException {
        if(authentication.getCredentials() == null) {
            this.logger.debug("Authentication failed: no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("PhoneAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            String presentedPassword = authentication.getCredentials().toString();
            //TODO 验证手机号和验证码的匹配关系
            if(presentedPassword.isEmpty()){
                this.logger.debug("Authentication failed: verifyCode does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("PhoneAuthenticationProvider.badCredentials", "Bad verifyCode"));
            }
        }
    }

    @Override
    protected Authentication createSuccessAuthentication(Object principal, Authentication authentication, UserDetails user) {
        PhoneAuthenticationToken result = new PhoneAuthenticationToken(principal, authentication.getCredentials(), user.getAuthorities());
        result.setDetails(authentication.getDetails());
        return result;
    }

    @Override
    protected UserDetails retrieveUser(String phone, Authentication authentication) throws AuthenticationException {
        return new User(authentication.getPrincipal().toString(), authentication.getCredentials().toString(), new ArrayList<>());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return PhoneAuthenticationToken.class.isAssignableFrom(authentication);
    }

}
