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

    /**
     * 成功 Static factory method for success
     *
     * @param claims 权利要求
     * @return {@code JWTValidationResultDTO }
     */
    public static JWTValidationResultDTO success(Claims claims) {
        return new JWTValidationResultDTO(true, null, claims);
    }

    /**
     * 失败 Static factory method for failure
     *
     * @param errorMessage 错误消息
     * @return {@code JWTValidationResultDTO }
     */
    public static JWTValidationResultDTO failure(String errorMessage) {
        return new JWTValidationResultDTO(false, errorMessage, null);
    }
}
