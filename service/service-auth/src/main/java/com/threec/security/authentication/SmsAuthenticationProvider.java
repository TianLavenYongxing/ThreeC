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
        PhoneNumberAuthenticationToken authenticationToken = (PhoneNumberAuthenticationToken) authentication;
        String mobile = (String) authenticationToken.getPrincipal();
        String code = authenticationToken.getPassword();
        // todo redis之类通过手机号查询验证码后 比较
        UserDetails userDetails = userDetailsService.loadUserByUsername(mobile);
        PhoneNumberAuthenticationToken phoneNumberAuthenticationToken = new PhoneNumberAuthenticationToken(userDetails, userDetails.getAuthorities());
        phoneNumberAuthenticationToken.setDetails(phoneNumberAuthenticationToken.getDetails());
        return phoneNumberAuthenticationToken;
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (PhoneNumberAuthenticationToken.class.isAssignableFrom(authentication));
    }
}
