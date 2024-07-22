package com.threec.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 用户名身份验证请求dto
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/22 16:50
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class UserNameAuthenticationRequestDTO extends AuthenticationRequestDTO{
    @ApiModelProperty("用户名登陆")
    private String username;
    @ApiModelProperty("密码")
    private String password;
}
