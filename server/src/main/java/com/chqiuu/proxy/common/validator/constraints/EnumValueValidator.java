package com.chqiuu.proxy.common.validator.constraints;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Modifier;

/**
 * 枚举类校验
 *
 * @author chqiu
 */
public class EnumValueValidator implements ConstraintValidator<EnumValue, Object> {
    /**
     * 枚举类对象
     */
    private Class<? extends Enum<?>> enumClass;
    /**
     * 枚举校验的方法
     */
    private String enumMethod;
    /**
     * 是否允许为空
     */
    private boolean allowNull;

    @Override
    public void initialize(EnumValue enumValue) {
        enumMethod = enumValue.enumMethod();
        enumClass = enumValue.enumClass();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext constraintValidatorContext) {
        if (value == null) {
            return allowNull;
        }
        if (enumClass == null || enumMethod == null) {
            return Boolean.TRUE;
        }
        Class<?> valueClass = value.getClass();
        try {
            Method method = enumClass.getMethod(enumMethod, valueClass);
            if (!Boolean.TYPE.equals(method.getReturnType()) && !Boolean.class.equals(method.getReturnType())) {
                throw new RuntimeException(String.format("%s method return is not boolean type in the %s class", enumMethod, enumClass));
            }
            if (!Modifier.isStatic(method.getModifiers())) {
                throw new RuntimeException(String.format("%s method is not static method in the %s class", enumMethod, enumClass));
            }
            Boolean result = (Boolean) method.invoke(null, value);
            return result == null ? false : result;
        } catch (IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            throw new RuntimeException(e);
        } catch (NoSuchMethodException | SecurityException e) {
            throw new RuntimeException(String.format("This %s(%s) method does not exist in the %s", enumMethod, valueClass, enumClass), e);
        }
    }
}
