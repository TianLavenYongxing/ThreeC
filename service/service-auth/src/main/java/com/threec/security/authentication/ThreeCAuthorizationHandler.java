package com.threec.security.authentication;

import com.threec.dao.SysUserDao;
import com.threec.dto.AuthUrlMethodDTO;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.access.intercept.RequestAuthorizationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;

import java.util.List;
import java.util.function.Supplier;

@Component
@RequiredArgsConstructor
public class ThreeCAuthorizationHandler implements AuthorizationManager<RequestAuthorizationContext> {

    private final SysUserDao UserDao;

    @Override
    public void verify(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        AuthorizationManager.super.verify(authentication, object);
    }

    @Override
    public AuthorizationDecision check(Supplier<Authentication> authentication, RequestAuthorizationContext object) {
        HttpServletRequest request = object.getRequest();
        String url = request.getRequestURI();
        String httpMethod = request.getMethod();
        String username = authentication.get().getName();
        List<AuthUrlMethodDTO> authUrlMethods = UserDao.authByUsername(username, httpMethod);
        AntPathMatcher antPathMatcher = new AntPathMatcher();
        for (AuthUrlMethodDTO dto : authUrlMethods) {
            boolean match = antPathMatcher.match(dto.getUrl(), url);
            if (match) {
                return new AuthorizationDecision(true);
            }
        }
        return new AuthorizationDecision(false);
    }
}
