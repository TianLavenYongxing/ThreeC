package com.threec.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 电话号码认证请求dto
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/22 16:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class PhoneNumberAuthenticationRequestDTO extends AuthenticationRequestDTO{
    @ApiModelProperty("手机号")
    private String phoneNumber;
    @ApiModelProperty("密码")
    private String password;
}
