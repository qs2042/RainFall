package com.qing.erp.member.advice;

import com.qing.erp.common.data.R;
import lombok.val;
import org.springframework.validation.BindException;
import org.springframework.web.bind.annotation.ExceptionHandler;

//import org.springframework.web.bind.annotation.ControllerAdvice;
//import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.stream.Collectors;

//@ControllerAdvice
//@ResponseBody
@RestControllerAdvice(basePackages = "com.qing.member.controller")
public class GlobalExceptionControllerAdvice {

    // Spring的Validation验证错误
    @ExceptionHandler(BindException.class)
    public R handleValidation(BindException e) {
        val result = e.getBindingResult();
        val list = result.getFieldErrors().stream().map(v -> new HashMap<String, String>(){{
            put("field", v.getField());
            put("msg", v.getDefaultMessage());
        }}).collect(Collectors.toList());
        return R.error().dataAdd("list", list);
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
