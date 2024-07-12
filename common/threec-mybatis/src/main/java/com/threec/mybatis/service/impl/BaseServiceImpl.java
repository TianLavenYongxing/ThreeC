package com.threec.mybatis.service.impl;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.threec.mybatis.service.BaseService;

/**
 * 基本服务实施
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/12 16:42
 */
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T> implements BaseService<T> {
    // todo 完善修改和逻辑删除功能 使用SecurityUserUtils获取修改人
}
