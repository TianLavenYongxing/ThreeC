package com.threec.dao;

import com.threec.dto.AuthUrlMethodDTO;
import com.threec.dto.AuthenticationUserDTO;
import com.threec.entity.SysUserEntity;
import com.threec.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 系统用户表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since  2024-07-19
 */
@Mapper
public interface SysUserDao extends BaseDao<SysUserEntity> {
    AuthenticationUserDTO findByUsername(@Param("username") String username);

    AuthenticationUserDTO findByPhoneNumber(@Param("phoneNumber") String phone);

    List<AuthUrlMethodDTO> authByUsername(@Param("username") String username, @Param("method") String method);
}