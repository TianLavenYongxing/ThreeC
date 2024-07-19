package com.threec.entity;

import com.baomidou.mybatisplus.annotation.TableName;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;



/**
 * 系统角色用户关联表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since  2024-07-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@TableName("sys_user_role")
public class SysUserRoleEntity {

    /**
     * 用户ID
     */
	private Integer userId;
    /**
     * 角色ID
     */
	private Integer roleId;
}