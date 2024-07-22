package com.threec.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.threec.dao.SysUserDao;
import com.threec.dto.AuthenticationRequestDTO;
import com.threec.dto.AuthenticationResponseDTO;
import com.threec.dto.AuthenticationUserDTO;
import com.threec.dto.UserNameAuthenticationRequestDTO;
import com.threec.dto.PhoneNumberAuthenticationRequestDTO;
import com.threec.entity.SysUserEntity;
import com.threec.tools.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import com.threec.security.authentication.PhoneNumberAuthenticationToken;
import com.threec.constant.AuthConstant;


import java.io.IOException;

/**
 * 认证服务
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/19 13:54
 */
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;
    private final SysUserDao userDao;
    private final JwtService jwtService;

    public AuthenticationResponseDTO authenticate(AuthenticationRequestDTO request) {
        if (!(request instanceof UserNameAuthenticationRequestDTO userNameRequest)) {
            throw new BusinessException(1000);
        }
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(userNameRequest.getUsername(), userNameRequest.getPassword()));
        Object principal = authenticate.getPrincipal();
        if (!(principal instanceof User user)) {
            throw new BusinessException(500);
        }
        return getAuthenticationResponseDTO(user);
    }

    public AuthenticationResponseDTO phoneNumberAuthenticate(AuthenticationRequestDTO request) {
        if (!(request instanceof PhoneNumberAuthenticationRequestDTO phoneNumber)) {
            throw new BusinessException(1000);
        }
        Authentication authenticate = authenticationManager.authenticate(new PhoneNumberAuthenticationToken(phoneNumber.getPhoneNumber(), phoneNumber.getPassword()));
        Object principal = authenticate.getPrincipal();
        if (!(principal instanceof User user)) {
            throw new BusinessException(500);
        }
        return getAuthenticationResponseDTO(user);
    }

    /**
     * 注册
     *
     * @param authUser auth用户 authUser.getLoginMode()
     * @return {@code AuthenticationResponseDTO }
     */
    public AuthenticationResponseDTO register(AuthenticationUserDTO authUser) {
        authUser.setPassword(passwordEncoder.encode(authUser.getPassword()));
        SysUserEntity userEntity = new SysUserEntity();
        userEntity.setPhoneNumber(authUser.getPhoneNumber());
        userEntity.setEmail(authUser.getEmail());
        userEntity.setUsername(authUser.getUsername());
        userEntity.setPassword(authUser.getPassword());
        userDao.insert(userEntity);
        String jwtToken = jwtService.generateToken(authUser);
        String refreshToken = jwtService.generateRefreshToken(authUser);
        saveUserToken(authUser, jwtToken);
        return AuthenticationResponseDTO.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        final String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);
        final String refreshToken;
        final String username;
        if (authHeader == null || !authHeader.startsWith(AuthConstant.BEARER)) {
            return;
        }
        refreshToken = authHeader.substring(AuthConstant.BEARER.length());
        username = jwtService.extractUsername(refreshToken);
        if (username != null) {
            AuthenticationUserDTO authUser = userDao.findByUsername(username);
            if (jwtService.isTokenValid(refreshToken, authUser)) {
                String accessToken = jwtService.generateToken(authUser);
                revokeAllUserTokens(authUser);
                saveUserToken(authUser, accessToken);
                AuthenticationResponseDTO authResponse = AuthenticationResponseDTO.builder().accessToken(accessToken).refreshToken(refreshToken).build();
                new ObjectMapper().writeValue(response.getOutputStream(), authResponse);
            }
        }
    }

    private AuthenticationResponseDTO getAuthenticationResponseDTO(UserDetails authUser) {
        String refreshToken = jwtService.generateRefreshToken(authUser);
        String jwtToken = jwtService.generateToken(authUser);
        revokeAllUserTokens(authUser);
        saveUserToken(authUser, jwtToken);
        return AuthenticationResponseDTO.builder().accessToken(jwtToken).refreshToken(refreshToken).build();
    }

    private void revokeAllUserTokens(UserDetails user) {
        // todo redis 查询根据用户名查询令牌 后验证 为空直接返回 不为空设置过期后保存
    }

    private void saveUserToken(UserDetails user, String jwtToken) {
        // todo redis写入 根据userId 为key写入
    }

}
