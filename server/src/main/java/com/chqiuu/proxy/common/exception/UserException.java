package com.chqiuu.proxy.common.exception;


import com.chqiuu.proxy.common.domain.ResultEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Formatter;

/**
 * 自定义异常处理
 *
 * @author chqiu
 */
public class UserException extends RuntimeException {

    @Getter
    @Setter
    private ResultEnum result;

    public UserException() {
        super();
    }

    public UserException(ResultEnum result) {
        this(result, result.getMessage());
    }

    public UserException(ResultEnum result, String message) {
        super(message);
        this.result = result;
    }

    public UserException(ResultEnum result, String format, Object... args) {
        super(new Formatter().format(format, args).toString());
        this.result = result;
    }
}
