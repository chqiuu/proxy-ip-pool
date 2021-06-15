package com.chqiuu.proxy.common.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;

import java.io.Serializable;
import java.util.Formatter;

/**
 * 响应信息主体
 * @author chqiu
 */
@Getter
@ApiModel(value = "响应信息主体")
public class Result<T> implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 状态码：1成功，其他均为失败【详见错误状态码表】
     */
    @ApiModelProperty(value = "状态码")
    private int code;
    /**
     * 成功为success，其他为失败原因
     */
    @ApiModelProperty(value = "消息")
    private Object message = "success";
    /**
     * 数据结果集
     */
    @ApiModelProperty(value = "数据结果集")
    private T data;
    /**
     * 当前时间
     */
    @ApiModelProperty(value = "时间戳")
    private final long time = System.currentTimeMillis();

    public Result<T> setMessage(Object message) {
        this.message = message;
        return this;
    }

    public Result<T> setMessage(String format, Object... args) {
        this.message = new Formatter().format(format, args).toString();
        return this;
    }

    public Result() {
    }

    /**
     * 使用枚举类中模版消息
     *
     * @param result ResultEnum
     * @param data           数据结果集
     */
    private Result(ResultEnum result, T data) {
        this.code = result.getCode();
        this.message = result.getMessage();
        this.data = data;
    }

    public static <T> Result<T> ok() {
        return restResult(ResultEnum.SUCCESS, null, null);
    }

    public static <T> Result<T> ok(T data) {
        return restResult(ResultEnum.SUCCESS, null, data);
    }

    public static <T> Result<T> ok(T data, Object message) {
        return restResult(ResultEnum.SUCCESS, message, data);
    }

    public static <T> Result<T> failed(ResultEnum result) {
        return restResult(result, null, null);
    }

    public static <T> Result<T> failed(ResultEnum result, Object message) {
        return restResult(result, message, null);
    }

    public static <T> Result<T> failed(ResultEnum result, Object message, T data) {
        return restResult(result, message, data);
    }

    private static <T> Result<T> restResult(ResultEnum result, Object message, T data) {
        Result<T> apiResult = new Result<>(result, data);
        if (null != message) {
            apiResult.setMessage(message);
        }
        return apiResult;
    }
}
