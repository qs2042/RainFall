package com.qing.erp.oss.advice;

import com.qing.erp.common.data.E;
import com.qing.erp.common.data.R;
import lombok.val;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;

import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.stream.Collectors;

//@ControllerAdvice
//@ResponseBody
@RestControllerAdvice(basePackages = "com.qing.erp.oss.controller")
public class ControllerExceptionHandler {

    @ExceptionHandler(E.class)
    public R handleE(Throwable t) {
        return R.error(t.getMessage()).dataAdd("map", t);
    }

    @ExceptionHandler(Throwable.class)
    public R handleException(Throwable t) {
        return R.error(t.getMessage()).dataAdd("map", t);
    }

    @ExceptionHandler(Exception.class)
    public R handleException(Exception ex) {
        return R.error(ex.getMessage()).dataAdd("map", ex);
    }

}
