package com.threec.exception;

import com.threec.tools.exception.BusinessException;
import com.threec.tools.utils.R;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

@ControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Object> handleConversionProblem(BusinessException ex) {
        return new ResponseEntity<>(R.error(ex.getCode(), ex.getMsg()), HttpStatus.BAD_REQUEST);
    }


}
