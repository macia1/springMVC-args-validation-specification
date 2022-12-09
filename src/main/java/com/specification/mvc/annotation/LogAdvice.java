package com.specification.mvc.annotation;

import java.lang.annotation.*;

/**
 * @author zenggs
 * @Date 2022/12/9
 */
@Target({ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface LogAdvice {
    /**
     * 接口描述
     */
    String desc() default "";
}
