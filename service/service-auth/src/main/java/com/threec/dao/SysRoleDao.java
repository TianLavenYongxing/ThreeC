package com.threec.dao;

import com.threec.entity.SysRoleEntity;
import com.threec.mybatis.dao.BaseDao;
import org.apache.ibatis.annotations.Mapper;


/**
 * 系统角色表
 *
 * @author Laven tianlavenyongxing@gmail.com
 * @since 2024-07-19
 */
@Mapper
public interface SysRoleDao extends BaseDao<SysRoleEntity> {

}