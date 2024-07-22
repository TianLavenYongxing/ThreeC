package com.threec.filter;

import com.threec.constant.RedisConstant;
import com.threec.redis.utils.RedisUtils;
import com.threec.service.JwtService;
import com.threec.constant.AuthConstant;
import com.threec.dto.JWTValidationResultDTO;
import com.threec.tools.utils.MessageUtils;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Objects;

/**
 * jWT身份验证过滤器
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/19 14:07
 */
@Component
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Resource
    private JwtService jwtService;
    @Resource(name = "userDetailsService")
    private UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(@NonNull HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull FilterChain filterChain) throws ServletException, IOException {
        final String requestPath = request.getServletPath();
        if(Arrays.stream(AuthConstant.API_AUTH).anyMatch(requestPath::contains)){
            filterChain.doFilter(request, response);
            return;
        }
        final String authHeader = request.getHeader(AuthConstant.AUTHORIZATION);
        final String jwt;
        jwt = authHeader.substring(AuthConstant.BEARER.length());
        String jwtRedis = RedisUtils.StringOps.get(RedisConstant.SYS_USER + jwtService.extractUsername(jwt));
        if(!Objects.equals(jwt,jwtRedis)){
            logger.info(MessageUtils.getMessage(1001));
        }
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            JWTValidationResultDTO jwtResult = jwtService.validateToken(jwt);
            if (!jwtResult.isValid()) {
                throw new JwtException(jwtResult.getErrorMessage());
            }
            Claims claims = jwtResult.getClaims();
            String username = claims.getSubject();
            if (username != null) {
                UserDetails userDetails = this.userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }
        filterChain.doFilter(request, response);
    }
}
