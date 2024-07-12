package com.threec.tools.utils;

import com.threec.tools.exception.ErrorCode;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.*;

import java.io.Serial;
import java.io.Serializable;

/**
 * R 响应结果
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/12 03:13:35
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@ApiModel("响应结果")
public class R<T> implements Serializable {
    @Serial
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "编码：200表示成功，其他值表示失败")
    private int code = 0;

    @ApiModelProperty(value = "消息内容")
    private String msg = "success";

    @ApiModelProperty(value = "响应数据")
    private T data;

    public R<T> ok(T data) {
        this.setData(data);
        return this;
    }

    public R<T> error() {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = MessageUtils.getMessage(this.code);
        return this;
    }

    public R<T> error(int code) {
        this.code = code;
        this.msg = MessageUtils.getMessage(this.code);
        return this;
    }

    public R<T> error(String msg) {
        this.code = ErrorCode.INTERNAL_SERVER_ERROR;
        this.msg = msg;
        return this;
    }

    public R<T> error(int code, String msg) {
        this.code = code;
        this.msg = msg;
        return this;
    }
}
