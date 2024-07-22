package com.threec.controller;

import com.threec.dto.AuthenticationRequestDTO;
import com.threec.dto.AuthenticationResponseDTO;
import com.threec.dto.AuthenticationUserDTO;
import com.threec.service.AuthenticationService;
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
        return ResponseEntity.ok(service.authenticate(request));
    }

    @PostMapping("/register")
    public ResponseEntity<AuthenticationResponseDTO> register(@Valid @RequestBody AuthenticationUserDTO user) {
        return ResponseEntity.ok(service.register(user));
    }

    @PostMapping("/smsAuthenticate")
    public ResponseEntity<AuthenticationResponseDTO> smsAuthenticate(@RequestBody AuthenticationRequestDTO request) {
        return ResponseEntity.ok(service.smsAuthenticate(request));
    }

    @PostMapping("/refresh-token")
    public void refreshToken(HttpServletRequest request, HttpServletResponse response) throws IOException {
        service.refreshToken(request, response);
    }

}
