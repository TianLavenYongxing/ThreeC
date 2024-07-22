package com.threec.controller;

import com.threec.dto.*;
import com.threec.service.AuthenticationService;
import com.threec.tools.exception.BusinessException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/authenticate")
    public ResponseEntity<AuthenticationResponseDTO> authenticate(@RequestBody AuthenticationRequestDTO request) {
        AuthenticationResponseDTO responseDTO = switch (request.getLoginMode()) {
            case 1 -> service.authenticate(request);
            case 2 -> service.smsAuthenticate(request);
            default -> throw new BusinessException(500);
        };
        return ResponseEntity.ok(responseDTO);
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@Valid @RequestBody AuthenticationUserDTO user) {
        return ResponseEntity.ok(service.register(user));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

}
