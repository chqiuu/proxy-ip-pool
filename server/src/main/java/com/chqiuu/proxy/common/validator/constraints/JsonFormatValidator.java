package com.chqiuu.proxy.common.validator.constraints;

import com.alibaba.fastjson.JSON;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * JSON格式字符串校验
 *
 * @author chqiu
 */
public class JsonFormatValidator implements ConstraintValidator<JsonFormat, String> {
    @Override
    public void initialize(JsonFormat constraintAnnotation) {

    }

    @Override
    public boolean isValid(String json, ConstraintValidatorContext context) {
        try {
            JSON.parseObject(json);
            return true;
        } catch (Exception e) {
            try {
                JSON.parseArray(json);
                return true;
            } catch (Exception e1) {
                e.printStackTrace();
                return false;
            }
        }
    }
}
