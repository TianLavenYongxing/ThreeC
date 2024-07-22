package com.threec.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.Data;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 身份验证用户dto
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/19 13:54
 */
@Data
public class AuthenticationUserDTO implements UserDetails {

    @ApiModelProperty(value = "用户ID")
    private int userId;

    @ApiModelProperty("登陆方式")
    @NotNull
    private int loginMode;

    @ApiModelProperty("手机号")
    private String phoneNumber;

    @ApiModelProperty("邮箱")
    private String email;

    @NotBlank(message = "用户名不能为空或只含空白字符")
    @Pattern(regexp = "^[a-zA-Z0-9_]+$", message = "用户名只能包含字母、数字和下划线")
    @ApiModelProperty(value = "用户名-手机号")
    private String username;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "启用（0未启用，1启用）")
    private Boolean enabled;

    @ApiModelProperty(value = "帐户未过期（0过期，1未过期）")
    private Boolean accountNonExpired;

    @ApiModelProperty(value = "凭证未过期(0过期，1未过期)")
    private Boolean credentialsNonExpired;

    @ApiModelProperty(value = "帐户未锁定(0锁定，1未锁定)")
    private Boolean accountNonLocked;

    @ApiModelProperty(value = "用户角色")
    private List<String> roles;

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return this.roles.stream().map(SimpleGrantedAuthority::new).collect(Collectors.toList());
    }

    @Override
    public String getPassword() {
        return this.password;
    }

    @Override
    public String getUsername() {
        return this.username;
    }

    @Override
    public boolean isAccountNonExpired() {
        return this.accountNonExpired;
    }

    @Override
    public boolean isAccountNonLocked() {
        return this.accountNonLocked;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return this.credentialsNonExpired;
    }

    @Override
    public boolean isEnabled() {
        return this.enabled;
    }

}
