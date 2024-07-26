package com.threec.controller;

import com.threec.dto.*;
import com.threec.service.AuthenticationService;
import com.threec.service.SmsService;
import com.threec.tools.exception.BusinessException;
import com.threec.tools.utils.R;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;

/**
 * 认证控制器
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/19 13:55
 */
@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthenticationController {

    private final AuthenticationService service;
    private final SmsService smsService;

    /**
     * 认证  1 用户名密码验证
     *      2 手机号密码验证
     *      3 手机号短信验证码验证
     *
     * @param request 请求
     * @return {@code ResponseEntity<AuthenticationResponseDTO> }
     */
    @PostMapping("/authenticate")
    public R<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO request) {
        AuthenticationResponseDTO responseDTO = switch (request.getLoginMode()) {
            case 1 -> service.authenticate(request);
            case 2 -> service.phoneNumberAuthenticate(request);
            case 3 -> service.smsAuthenticate(request);
            default -> throw new BusinessException(500);
        };
        return R.ok(responseDTO);
    }

    @PostMapping("/register")
    public R<AuthenticationResponseDTO> register(@Valid @RequestBody AuthenticationUserDTO user) {
        return R.ok(service.register(user));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

    @PostMapping("/getSmsCaptcha")
    public R<Object> getSmsCaptcha(@Valid @RequestBody SmsAuthenticationRequestDTO dto){
        if(smsService.getSMSCaptcha(dto)){
           return R.ok(true);
        }
        return R.error();
    }

}
