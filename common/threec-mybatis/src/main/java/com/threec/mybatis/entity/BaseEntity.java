package com.threec.mybatis.entity;

import com.baomidou.mybatisplus.annotation.TableId;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

/**
 * 基本实体
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/12 16:33
 */
@Data
public class BaseEntity implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @TableId
    private Long id;
    /**
     * 创建人
     */
    private Long createBy;
    /**
     * 创建时间
     */
    private Date createAt;
    /**
     * 更新人
     */
    private Long updateBy;
    /**
     * 更新时间
     */
    private Date updateAt;
    /**
     * 删除人
     */
    private Long deleteBy;
    /**
     * 删除时间
     */
    private Date deleteAt;
    /**
     * 删除标志（0未删除，已删除）
     */
    private boolean delFlag;
}
