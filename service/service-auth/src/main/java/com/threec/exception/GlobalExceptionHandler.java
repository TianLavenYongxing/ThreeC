package com.threec.exception;

import com.threec.tools.exception.BusinessException;
import com.threec.tools.utils.R;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * 全局异常处理程序
 *
 * @author Tian, Laven Yongxing
 * @date 2024/07/21 19:00
 */
@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleConversionProblem(BusinessException ex) {
        return new ResponseEntity<>(R.error(ex.getCode(), ex.getMsg()), HttpStatus.BAD_REQUEST);
    }


}
