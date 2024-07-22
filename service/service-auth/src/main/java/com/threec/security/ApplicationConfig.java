package com.threec.security;

import com.alibaba.fastjson2.JSON;
import com.threec.constant.AuthConstant;
import com.threec.constant.RedisConstant;
import com.threec.dto.AuthenticationUserDTO;
import com.threec.redis.utils.RedisUtils;
import com.threec.security.authentication.PhoneNumberAuthenticationProvider;
import com.threec.service.JwtService;
import com.threec.tools.utils.R;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Value;
import com.threec.dao.SysUserDao;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.util.ObjectUtils;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.Arrays;
import java.util.Collections;
import java.util.Optional;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

/**
 * 应用程序配置
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/19 13:55
 */
@Configuration
public class ApplicationConfig {

    @Value("${application.security.b-crypt-password-encoder.strength}")
    private int strength;
    @Resource
    private JwtService jwtService;
    @Resource
    private SysUserDao userDao;

    @Bean(name = "userDetailsService")
    public UserDetailsService userDetailsService() {
        return username -> {
            AuthenticationUserDTO user = userDao.findByUsername(username);
            if (ObjectUtils.isEmpty(user)) {
                throw new UsernameNotFoundException(AuthConstant.ERROR_USERNAME_OR_PASSWORD);
            }
            return new User(user.getUsername(), user.getPassword(),  Optional.ofNullable(user.getRoles()).map(roles -> roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())).orElse(Collections.emptyList()));
        };
    }
    @Bean(name = "phoneNumberUserDetailsService")
    public UserDetailsService phoneNumberUserDetailsService() {
        return phoneNumber -> {
            AuthenticationUserDTO user = userDao.findByPhoneNumber(phoneNumber);
            if (ObjectUtils.isEmpty(user)) {
                throw new UsernameNotFoundException(AuthConstant.ERROR_USERNAME_OR_PASSWORD);
            }
            return new User(user.getUsername(), user.getPassword(), Optional.ofNullable(user.getRoles()).map(roles -> roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList())).orElse(Collections.emptyList()));
        };
    }

    @Bean(name = "authenticationProvider")
    public AuthenticationProvider authenticationProvider() {
        DaoAuthenticationProvider authProvider = new DaoAuthenticationProvider();
        authProvider.setUserDetailsService(userDetailsService());
        authProvider.setPasswordEncoder(passwordEncoder());
        return authProvider;
    }

    @Bean(name = "phoneNumberAuthenticationProvider")
    public AuthenticationProvider phoneNumberAuthenticationProvider() {
        PhoneNumberAuthenticationProvider phoneNumberAuthProvider = new PhoneNumberAuthenticationProvider();
        phoneNumberAuthProvider.setUserDetailsService(phoneNumberUserDetailsService());
        return phoneNumberAuthProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager() {
        return new ProviderManager(Arrays.asList(phoneNumberAuthenticationProvider(), authenticationProvider()));
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder(strength);
    }

    @Bean
    public AuthenticationEntryPoint authenticationEntryPoint() {
        return (request, response, authException) -> {
            // todo 逻辑 ThreeCLoginFailureHandler一样实现 authException逻辑
            response.setContentType(AuthConstant.CONTENT_TYPE);
            ServletOutputStream outputStream = response.getOutputStream();
            R<Object> r = R.error(HttpServletResponse.SC_UNAUTHORIZED, AuthConstant.AUTHENTICATION_FAILED);
            outputStream.write(JSON.toJSONString(r).getBytes());
            outputStream.flush();
            outputStream.close();
        };
    }

    @Bean
    public AccessDeniedHandler accessDeniedHandler() {
        return (request, response, accessDeniedException) -> {
            // todo 逻辑 ThreeCLoginFailureHandler一样实现 accessDeniedException逻辑
            response.setContentType(AuthConstant.CONTENT_TYPE);
            ServletOutputStream outputStream = response.getOutputStream();
            R<Object> r = R.error(HttpServletResponse.SC_FORBIDDEN, AuthConstant.ACCESS_DENIED);
            outputStream.write(JSON.toJSONString(r).getBytes());
            outputStream.flush();
            outputStream.close();
        };
    }

    @Bean
    public LogoutHandler LogoutHandler() {
        return (request, response, accessDeniedException) -> {
            String authHeader = request.getHeader(AuthConstant.AUTHORIZATION);
            if (authHeader == null || !authHeader.startsWith(AuthConstant.BEARER)) {
                return;
            }
            String jwt = authHeader.substring(7);
            String username = jwtService.extractUsername(jwt);
            RedisUtils.StringOps.setEx(RedisConstant.SYS_USER+username,jwt,10, TimeUnit.SECONDS);
            SecurityContextHolder.clearContext();
            response.setContentType(AuthConstant.CONTENT_TYPE);
            try {
                ServletOutputStream outputStream = response.getOutputStream();
                R<Object> r = R.error(HttpServletResponse.SC_OK, AuthConstant.LOGOUT_SUCCESS);
                outputStream.write(JSON.toJSONString(r).getBytes());
                outputStream.flush();
                outputStream.close();
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        };
    }

}
