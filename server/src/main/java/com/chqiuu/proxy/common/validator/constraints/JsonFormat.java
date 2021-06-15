package com.chqiuu.proxy.common.validator.constraints;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * JSON格式数据校验
 *
 * @author chqiu
 */
@Documented
@Target({ElementType.PARAMETER, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = JsonFormatValidator.class)
public @interface JsonFormat {
    String message() default "JSON格式有误！";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}