package com.threec.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.threec.mybatis.entity.BaseEntity;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;


/**
 * 系统角色表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since  2024-07-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@TableName("sys_role")
public class SysRoleEntity extends BaseEntity {

    /**
     * 角色
     */
	private String role;
    /**
     * 角色名
     */
	private String roleName;

}