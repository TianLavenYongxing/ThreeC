package com.threec.security.authentication;

import lombok.Getter;
import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

/**
 * 手机号码认证令牌
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/19 13:42
 */
public class PhoneNumberAuthenticationToken extends AbstractAuthenticationToken {
    private final Object principal;
    @Getter
    private String code;

    public PhoneNumberAuthenticationToken(Object principal, String code) {
        super(null);
        this.principal = principal;
        this.code = code;
        setAuthenticated(false);
    }

    public PhoneNumberAuthenticationToken(Object principal, Collection<? extends GrantedAuthority> authorities) {
        super(authorities);
        this.principal = principal;
        super.setAuthenticated(true); // must use super, as we override
    }

    @Override
    public Object getCredentials() {
        return null;
    }

    @Override
    public Object getPrincipal() {
        return this.principal;
    }

    @Override
    public void setAuthenticated(boolean isAuthenticated) throws IllegalArgumentException {
        super.setAuthenticated(false);
    }

}


