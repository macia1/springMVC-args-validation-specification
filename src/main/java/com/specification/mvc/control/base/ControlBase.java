package com.specification.mvc.control.base;

import lombok.extern.slf4j.Slf4j;
import org.springframework.validation.BindException;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author zenggs
 * @Date 2022/11/9
 */
@ControllerAdvice
@Slf4j
public class ControlBase {
    private static final String BIND_EXCEPTION = "绑定参数异常";
    private static final String METHOD_EXCEPTION = "方法参数异常";
    @ResponseBody
    @ExceptionHandler(BindException.class)
    public Object bindException(BindException bindException) {
        log.info(BIND_EXCEPTION);
        return BIND_EXCEPTION;
    }
    @ResponseBody
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public Object methodArgumentNotValidException(MethodArgumentNotValidException methodArgumentNotValidException) {
        List<FieldError> fieldErrors = methodArgumentNotValidException.getBindingResult().getFieldErrors();
        log.info(METHOD_EXCEPTION);
        return fieldErrors.stream().map(it->{
            Map<String, Object> resultMaps = new HashMap<>(16);
            resultMaps.put(it.getField(),it.getDefaultMessage());
            return resultMaps;
        }).collect(Collectors.toSet());
    }

    @ResponseBody
    @ExceptionHandler({Exception.class,Throwable.class})
    public String defaultException(Exception exception){
        log.error("请求异常:{}", exception.getMessage());
        return "系统繁忙";
    }

}
