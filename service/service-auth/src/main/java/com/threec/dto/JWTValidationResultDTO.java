package com.threec.dto;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * jwt验证结果dto
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/19 13:55
 */
@Data
@AllArgsConstructor
public class JWTValidationResultDTO {
    private boolean isValid;
    private String errorMessage;
    private Claims claims;
    // Static factory method for success
    public static JWTValidationResultDTO success(Claims claims) {
        return new JWTValidationResultDTO(true, null, claims);
    }

    // Static factory method for failure
    public static JWTValidationResultDTO failure(String errorMessage) {
        return new JWTValidationResultDTO(false, errorMessage, null);
    }
}
