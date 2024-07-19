package com.threec.dto;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Builder;
import lombok.Data;

/**
 * 身份验证响应dto
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/19 13:54
 */
@Data
@Builder
public class AuthenticationResponseDTO {
    @JsonProperty("access_token")
    private String accessToken;
    @JsonProperty("refresh_token")
    private String refreshToken;
}
