package com.chqiuu.proxy.modules.api.query;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

@Data
@ApiModel(value = "代理IP API分页查询对象")
public class ProxyIpCommonPageQuery implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 排序参数
     */
    @ApiModelProperty(value = "排序参数")
    private String sortParam;
    /**
     * 排序方式：正序asc，倒序desc，默认为desc
     */
    @ApiModelProperty(value = "排序方式：正序asc，倒序desc，默认为desc")
    private String sortord;
    /**
     * 当前页
     */
    @ApiModelProperty(value = "当前页")
    private Integer current = 1;
    /**
     * 每页显示条数
     */
    @ApiModelProperty(value = "每页显示条数")
    private Integer size = 10;

    /**
     * 支持https `https` tinyint(4) DEFAULT 0  COMMENT 支持https
     */
    @ApiModelProperty(value = "支持https")
    private Integer https;
    /**
     * 支持http `http` tinyint(4) DEFAULT 0  COMMENT 支持http
     */
    @ApiModelProperty(value = "支持http")
    private Integer http;
    /**
     * 匿名性 `anonymity` tinyint(4) DEFAULT 0  COMMENT 匿名性
     */
    @ApiModelProperty(value = "匿名性")
    private Integer anonymity;
}
