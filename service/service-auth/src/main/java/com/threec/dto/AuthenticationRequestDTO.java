package com.threec.dto;

import lombok.Data;

/**
 * 身份验证请求dto
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/19 13:45
 */
@Data
public class AuthenticationRequestDTO {
    private String mobile;
    private String password;
    private String code;
}
