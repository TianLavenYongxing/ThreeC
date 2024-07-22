package com.threec.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.JsonTypeInfo.As;
import com.fasterxml.jackson.annotation.JsonTypeInfo.Id;

/**
 * 身份验证请求dto
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/19 13:45
 */
@JsonTypeInfo(use = Id.NAME, include = As.PROPERTY, property = "type")
@JsonSubTypes({
        @JsonSubTypes.Type(value = UserNameAuthenticationRequestDTO.class, name = "username"),
        @JsonSubTypes.Type(value = SmsAuthenticationRequestDTO.class, name = "sms")
})
@Getter
@Setter
public abstract class AuthenticationRequestDTO {
    @ApiModelProperty("验证码")
    private String code;
    @ApiModelProperty("登陆方式")
    @NotNull
    private int loginMode;
}
