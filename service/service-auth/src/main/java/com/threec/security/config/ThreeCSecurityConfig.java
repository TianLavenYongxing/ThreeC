package com.threec.security.config;

import com.threec.constant.AuthConstant;
import com.threec.filter.JwtAuthenticationFilter;
import com.threec.security.authentication.ThreeCAuthorizationHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;


import javax.annotation.Resource;

@Configuration
@EnableWebSecurity
public class ThreeCSecurityConfig {
    @Resource
    private JwtAuthenticationFilter jwtAuthFilter;
    @Resource
    private ThreeCAuthorizationHandler threeCAuthorizationHandler;
    @Resource
    private AccessDeniedHandler accessDeniedHandler;
    @Resource
    private LogoutHandler logoutHandler;
    @Resource(name = "authenticationEntryPoint")
    private AuthenticationEntryPoint authenticationEntryPoint;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.csrf(AbstractHttpConfigurer::disable);         // 关闭csrf攻击防御
        http.cors(Customizer.withDefaults());               // 跨域问题
        http.authorizeHttpRequests(auth -> auth.requestMatchers(AuthConstant.WHITE_LIST_URL).permitAll().anyRequest().access(threeCAuthorizationHandler));  //开启授权保护  anyRequest() 对所有请求开启授权保护  access()自定义授权
        http.sessionManagement(s -> s.sessionCreationPolicy(SessionCreationPolicy.STATELESS)); // 无状态配置：通过
        http.addFilterBefore(jwtAuthFilter, UsernamePasswordAuthenticationFilter.class);
        http.exceptionHandling(e -> {
            e.accessDeniedHandler(accessDeniedHandler);
            e.authenticationEntryPoint(authenticationEntryPoint);
        });
        http.logout(logout -> logout.logoutUrl(AuthConstant.AUTH_LOGOUT).addLogoutHandler(logoutHandler).logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext()));
        return http.build();
    }

}
