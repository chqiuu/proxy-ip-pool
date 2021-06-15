package com.chqiuu.proxy.common.validator.constraints;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;

/**
 * 时间区间校验，结束时间必须在开始时间之后
 *
 * @author chqiu
 */
public class TimeIntervalValidator implements ConstraintValidator<TimeInterval, Object> {
    /**
     * 开始时间变量名
     */
    private String beginTime;
    /**
     * 截止时间变量名
     */
    private String endTime;

    @Override
    public void initialize(TimeInterval constraintAnnotation) {
        this.beginTime = constraintAnnotation.beginTime();
        this.endTime = constraintAnnotation.endTime();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        if (null == value) {
            return false;
        }
        BeanWrapper wrapper = new BeanWrapperImpl(value);
        Object begin = wrapper.getPropertyValue(beginTime);
        Object end = wrapper.getPropertyValue(endTime);
        if (null == begin || null == end) {
            return false;
        }
        int r = ((LocalDateTime) end).compareTo((LocalDateTime) begin);
        if (r >= 0) {
            return true;
        }
        return false;
    }
}
