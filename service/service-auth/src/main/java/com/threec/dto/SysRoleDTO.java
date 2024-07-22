package com.threec.dto;

import com.threec.mybatis.dto.BashDTO;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.EqualsAndHashCode;
import lombok.Data;


/**
 * 系统角色表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since  2024-07-19
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "系统角色表")
public class SysRoleDTO extends BashDTO {

	@ApiModelProperty(value = "角色")
	private String role;

	@ApiModelProperty(value = "角色名")
	private String roleName;

}