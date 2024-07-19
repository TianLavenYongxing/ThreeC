package com.threec.mybatis.dto;

import io.swagger.annotations.ApiModelProperty;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.io.Serial;
import java.io.Serializable;
import java.util.Date;

@Data
public class BashDTO implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @NotNull
    @ApiModelProperty(value = "ID")
    private Integer id;

    @NotNull
    @ApiModelProperty(value = "删除标志（0已删除，1未删除）")
    private Integer delFlag;

//    @NotNull
//    @ApiModelProperty(value = "创建时间")
//    private Integer createBy;
//
//    @NotNull
//    @ApiModelProperty(value = "创建人")
//    private Date createTime;
//
//    @ApiModelProperty(value = "更新时间")
//    private Integer updateBy;
//
//    @ApiModelProperty(value = "更新人")
//    private Date updateTime;
//
//    @ApiModelProperty(value = "删除时间")
//    private Date delTime;
//
//    @ApiModelProperty(value = "删除人")
//    private Integer delBy;

}
