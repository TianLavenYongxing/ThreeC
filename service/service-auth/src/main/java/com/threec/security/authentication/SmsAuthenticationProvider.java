package com.threec.security.authentication;

import com.alibaba.druid.util.StringUtils;
import lombok.Setter;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;

import javax.annotation.Resource;

@Setter
public class SmsAuthenticationProvider implements AuthenticationProvider {
    protected final Log logger = LogFactory.getLog(this.getClass());
    protected MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();
    @Resource(name = "smsUserDetailsService")
    private UserDetailsService userDetailsService;
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        SmsAuthenticationToken authenticationToken = (SmsAuthenticationToken) authentication;
        String phoneNumber = (String) authenticationToken.getPrincipal();
        UserDetails userDetails = userDetailsService.loadUserByUsername(phoneNumber);
        additionalAuthenticationChecks(userDetails, authenticationToken);
        return new SmsAuthenticationToken(userDetails, userDetails.getAuthorities());
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return (SmsAuthenticationToken.class.isAssignableFrom(authentication));
    }

    protected void additionalAuthenticationChecks(UserDetails userDetails, SmsAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            this.logger.debug("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
        } else {
            String presentedPassword = authentication.getCredentials().toString();
            if (!StringUtils.equals(presentedPassword, userDetails.getPassword())) {
                this.logger.debug("Failed to authenticate since password does not match stored value");
                throw new BadCredentialsException(this.messages.getMessage("AbstractUserDetailsAuthenticationProvider.badCredentials", "Bad credentials"));
            }
        }
    }
}
