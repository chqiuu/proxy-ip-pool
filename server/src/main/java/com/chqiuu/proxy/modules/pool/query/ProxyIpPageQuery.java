package com.chqiuu.proxy.modules.pool.query;

import java.io.Serializable;
import java.util.Date;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import java.math.BigDecimal;
import com.alibaba.fastjson.JSONObject;

import lombok.Data;
import lombok.EqualsAndHashCode;
import java.time.LocalDateTime;
import java.time.LocalDate;

/**
 * 代理IP分页查询对象
 *
 * @author chqiuu
 * @date 2021年6月11日
 */
@Data
@ApiModel(value = "代理IP分页查询对象")
public class ProxyIpPageQuery implements Serializable{

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

//TODO 当您看到这个后您应该自己修改模板增减规则
    /**
     * IP地址 `ip_address` varchar(255)  COMMENT IP地址
     */
    @ApiModelProperty(value = "IP地址")
    private String ipAddress;
    /**
     * IP端口 `ip_port` int(11)  COMMENT IP端口
     */
    @ApiModelProperty(value = "IP端口")
    private Integer ipPort;
    /**
     * 国家 `country` varchar(255)  COMMENT 国家
     */
    @ApiModelProperty(value = "国家")
    private String country;
    /**
     * 位置 `location` varchar(255)  COMMENT 位置
     */
    @ApiModelProperty(value = "位置")
    private String location;
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
    /**
     * 可用性 `available` tinyint(4) DEFAULT 0  COMMENT 可用性
     */
    @ApiModelProperty(value = "可用性")
    private Integer available;
    /**
     * 最近校验时间 `last_validate_time` datetime  COMMENT 最近校验时间
     */
    @ApiModelProperty(value = "最近校验时间")
    private LocalDateTime lastValidateTime;
    /**
     * 校验次数 `validate_count` int(11)  COMMENT 校验次数
     */
    @ApiModelProperty(value = "校验次数")
    private Integer validateCount;
    /**
     * 校验可用次数 `available_count` int(11)  COMMENT 校验可用次数
     */
    @ApiModelProperty(value = "校验可用次数")
    private Integer availableCount;
    /**
     * 校验不可用次数 `unavailable_count` int(11)  COMMENT 校验不可用次数
     */
    @ApiModelProperty(value = "校验不可用次数")
    private Integer unavailableCount;
    /**
     * 失效时间 `failure_time` datetime  COMMENT 失效时间
     */
    @ApiModelProperty(value = "失效时间")
    private LocalDateTime failureTime;
    /**
     * 代理请求需要总时长 `use_times` int(11)  COMMENT 代理请求需要总时长
     */
    @ApiModelProperty(value = "代理请求需要总时长")
    private Integer useTimes;
    /**
     * 可用率 `available_rate` double(6,4)  COMMENT 可用率
     */
    @ApiModelProperty(value = "可用率")
    private Double availableRate;
    /**
     * 使用次数 `use_size` int(11) unsigned DEFAULT 0  COMMENT 使用次数
     */
    @ApiModelProperty(value = "使用次数")
    private Integer useSize;
    /**
     * 失败次数 `fail_size` int(11) unsigned DEFAULT 0  COMMENT 失败次数
     */
    @ApiModelProperty(value = "失败次数")
    private Integer failSize;
}