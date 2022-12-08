package com.specification.mvc.control.handle;

import javax.validation.Constraint;
import javax.validation.Payload;
import javax.validation.constraints.NotBlank;
import java.lang.annotation.*;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * @author zenggs
 * @Date 2022/11/14
 */
@Target({ElementType.TYPE,METHOD, FIELD,ElementType.ANNOTATION_TYPE,ElementType.CONSTRUCTOR,ElementType.PARAMETER, PACKAGE})
@Retention(RUNTIME)
@Documented
// 校验执行类
@Constraint(validatedBy = {UserValidator.class})
public @interface UserValidate {
    // default error message
    String message() default "用户信息校验失败.";
    Class<?>[] groups() default {};
    Class<? extends Payload>[] payload() default { };

    /**
     * 同一个元素上指定多个该注解时使用
     */
    @Target({ METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER, TYPE_USE })
    @Retention(RUNTIME)
    @Documented
    public @interface List {
        NotBlank[] value();
    }
}
