package com.threec.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class UserNameAuthenticationRequestDTO extends AuthenticationRequestDTO{
    @ApiModelProperty("用户名登陆")
    private String username;
    @ApiModelProperty("密码")
    private String password;
}
