package com.threec.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import com.threec.mybatis.dto.BashDTO;

import lombok.EqualsAndHashCode;
import lombok.Data;


/**
 * 系统用户表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since  2024-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统用户表")
public class SysUserDTO extends BashDTO{
	@ApiModelProperty(value = "用户名")
	private String username;

	@ApiModelProperty(value = "昵称")
	private String nickName;

	@ApiModelProperty(value = "密码")
	private String password;

	@ApiModelProperty(value = "邮箱")
	private String email;

	@ApiModelProperty(value = "手机号")
	private String phoneNumber;

	@ApiModelProperty(value = "0男,1女,2跨性别,3未知")
	private Integer sex;

	@ApiModelProperty(value = "头像")
	private String avatar;

	@ApiModelProperty(value = "启用（0未启用，1启用）")
	private Boolean enabled;

	@ApiModelProperty(value = "帐户未过期（0过期，1未过期）")
	private Boolean accountNonExpired;

	@ApiModelProperty(value = "凭证未过期(0过期，1未过期)")
	private Boolean credentialsNonExpired;

	@ApiModelProperty(value = "帐户未锁定(0锁定，1未锁定)")
	private Boolean accountNonLocked;

}