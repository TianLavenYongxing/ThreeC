package com.threec.mybatis.entity;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableLogic;
import jakarta.validation.constraints.NotNull;
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
    @TableField(fill = FieldFill.INSERT)
    private Long createBy;
    /**
     * 创建时间
     */
    @TableField(fill = FieldFill.INSERT)
    @NotNull
    private Date createAt;
    /**
     * 更新人
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long updateBy;
    /**
     * 更新时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date updateAt;
    /**
     * 删除人
     */
    @TableField(fill = FieldFill.UPDATE)
    private Long deleteBy;
    /**
     * 删除时间
     */
    @TableField(fill = FieldFill.UPDATE)
    private Date deleteAt;
    /**
     * 删除标志（0未删除，已删除）
     */
    @TableLogic
    @NotNull
    private boolean delFlag;
}
