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

    public static <T> R<T> ok(T data) {
        R<T> result = new R<>();
        result.setData(data);
        return result;
    }

    public static <T> R<T> error() {
        R<T> result = new R<>();
        result.setCode(ErrorCode.INTERNAL_SERVER_ERROR);
        result.setMsg(MessageUtils.getMessage(ErrorCode.INTERNAL_SERVER_ERROR));
        return result;
    }

    public static <T> R<T> error(int code) {
        R<T> result = new R<>();
        result.setCode(code);
        result.setMsg(MessageUtils.getMessage(code));
        return result;
    }

    public static <T> R<T> error(String msg) {
        R<T> result = new R<>();
        result.setCode(ErrorCode.INTERNAL_SERVER_ERROR);
        result.setMsg(msg);
        return result;
    }

    public static <T> R<T> error(int code, String msg) {
        R<T> result = new R<>();
        result.setCode(code);
        result.setMsg(msg);
        return result;
    }
}
