package com.threec.security.authentication;

import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

public class SmsAuthenticationProvider implements AuthenticationProvider {

    private UserDetailsService userDetailsService;

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String code = authenticationToken.getCode();
        // todo redis之类通过手机号查询验证码后 比较
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        SmsAuthenticationToken smsAuthenticationToken = new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
        smsAuthenticationToken.setDetails(smsAuthenticationToken.getDetails());
        return smsAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (SmsAuthenticationToken.class.isAssignableFrom(authentication));
    }

    public void setUserDetailsService(UserDetailsService userDetailsService) {
        this.userDetailsService = userDetailsService;
    }
}
