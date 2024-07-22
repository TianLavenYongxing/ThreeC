package com.threec.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class SmsAuthenticationRequestDTO extends AuthenticationRequestDTO{
    @ApiModelProperty("手机号")
    private String phoneNumber;
    @ApiModelProperty("密码")
    private String password;
}
