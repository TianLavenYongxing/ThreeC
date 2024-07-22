package com.threec.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.threec.mybatis.entity.BaseEntity;

import lombok.Data;
import lombok.experimental.Accessors;
import lombok.EqualsAndHashCode;

import java.util.Collection;


/**
 * 系统用户表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since  2024-07-19
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper=false)
@TableName("sys_user")
public class SysUserEntity extends BaseEntity {

    /**
     * 用户名
     */
	private String username;
    /**
     * 昵称
     */
	private String nickName;
    /**
     * 密码
     */
	private String password;
    /**
     * 邮箱
     */
	private String email;
    /**
     * 手机号
     */
	private String phoneNumber;
    /**
     * 0男,1女,2跨性别,3未知
     */
	private Integer sex;
    /**
     * 头像
     */
	private String avatar;
    /**
     * 启用（0未启用，1启用）
     */
	private Boolean enabled;
    /**
     * 帐户未过期（0过期，1未过期）
     */
	private Boolean accountNonExpired;
    /**
     * 凭证未过期(0过期，1未过期)
     */
	private Boolean credentialsNonExpired;
    /**
     * 帐户未锁定(0锁定，1未锁定)
     */
	private Boolean accountNonLocked;
    private Collection<String> roles;
}