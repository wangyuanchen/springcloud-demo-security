package com.zhao.cloud.gateway.auth.config;

import com.zhao.cloud.gateway.auth.extend.filter.CompanyLoginAuthenticationFilter;
import com.zhao.cloud.gateway.auth.extend.filter.PhoneLoginAuthenticationFilter;
import com.zhao.cloud.gateway.auth.extend.handler.CustomAuthSuccessHandler;
import com.zhao.cloud.gateway.auth.extend.handler.CustomAuthenticationFailureHandler;
import com.zhao.cloud.gateway.auth.extend.provider.CompanyAuthenticationProvider;
import com.zhao.cloud.gateway.auth.extend.provider.PhoneAuthenticationProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

/**
 * 配合基于web的security
 *
 * @author zhaoliang
 */
@Configuration
@EnableWebSecurity
public class WebSecurityConfig extends WebSecurityConfigurerAdapter {
    @Autowired
    private CustomAuthSuccessHandler customAuthSuccessHandler;
    @Autowired
    private CustomAuthenticationFailureHandler customAuthenticationFailureHandler;

    @Override
    protected void configure(AuthenticationManagerBuilder authManagerBuilder) {
        authManagerBuilder.authenticationProvider(phoneAuthenticationProvider());
        authManagerBuilder.authenticationProvider(companyAuthenticationProvider());
    }

    @Bean
    public PhoneAuthenticationProvider phoneAuthenticationProvider() {
        PhoneAuthenticationProvider provider = new PhoneAuthenticationProvider();
        // 禁止隐藏用户未找到异常
        provider.setHideUserNotFoundExceptions(false);
        return provider;
    }

    @Bean
    public CompanyAuthenticationProvider companyAuthenticationProvider() {
        return new CompanyAuthenticationProvider();
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .addFilterBefore(getPhoneLoginAuthenticationFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(getCompanyLoginAuthenticationFilter(), BasicAuthenticationFilter.class)
                .authorizeRequests()
                .anyRequest().permitAll()
                .and().csrf().disable();
    }

    private PhoneLoginAuthenticationFilter getPhoneLoginAuthenticationFilter() {
        PhoneLoginAuthenticationFilter filter = new PhoneLoginAuthenticationFilter();
        try {
            filter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        filter.setAuthenticationSuccessHandler(customAuthSuccessHandler);
        return filter;
    }

    private CompanyLoginAuthenticationFilter getCompanyLoginAuthenticationFilter() {
        CompanyLoginAuthenticationFilter filter = new CompanyLoginAuthenticationFilter();
        try {
            filter.setAuthenticationManager(this.authenticationManagerBean());
        } catch (Exception e) {
            e.printStackTrace();
        }
        filter.setAuthenticationFailureHandler(customAuthenticationFailureHandler);
        filter.setAuthenticationSuccessHandler(customAuthSuccessHandler);
        return filter;
    }
}
