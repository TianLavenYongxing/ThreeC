package com.threec.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = true)
public class SmsAuthenticationRequestDTO extends AuthenticationRequestDTO {
    @ApiModelProperty("手机号")
    private String phoneNumber;
    @ApiModelProperty("短信验证码")
    private String smsCode;
}
