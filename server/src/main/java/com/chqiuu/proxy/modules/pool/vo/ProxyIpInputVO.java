package com.chqiuu.proxy.modules.pool.vo;

import java.io.Serializable;

import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import lombok.Data;
import com.chqiuu.proxy.common.validator.group.Default;
import com.chqiuu.proxy.common.validator.group.Update;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

/**
 * 代理IP录入信息
 *
 * @author chqiuu
 * @date 2021年6月11日
 */
@Data
@ApiModel(value = "代理IP录入信息")
public class ProxyIpInputVO implements Serializable{

    private static final long serialVersionUID = 1L;
    //TODO 当您看到这个后您应该自己修改模板增减规则
    /**
     * 唯一ID `proxy_id` varchar(30)  COMMENT 唯一ID
     */
    @NotNull(message = "唯一ID不能为空", groups = Update.class)
    @Length(max = 30, message = "唯一ID不能超过{max}个字符", groups = Default.class)
    @ApiModelProperty(value = "唯一ID")
    private String proxyId;
    /**
     * IP地址 `ip_address` varchar(255)  COMMENT IP地址
     */
    @NotNull(message = "IP地址不能为空", groups = Default.class)
    @Length(max = 255, message = "IP地址不能超过{max}个字符", groups = Default.class)
    @ApiModelProperty(value = "IP地址")
    private String ipAddress;
    /**
     * IP端口 `ip_port` int(11)  COMMENT IP端口
     */
    @NotNull(message = "IP端口不能为空", groups = Default.class)
    @Max(value = Integer.MAX_VALUE, message = "IP端口不能超过{value}", groups = Default.class)
    @ApiModelProperty(value = "IP端口")
    private Integer ipPort;
    /**
     * 国家 `country` varchar(255)  COMMENT 国家
     */
    @NotNull(message = "国家不能为空", groups = Default.class)
    @Length(max = 255, message = "国家不能超过{max}个字符", groups = Default.class)
    @ApiModelProperty(value = "国家")
    private String country;
    /**
     * 位置 `location` varchar(255)  COMMENT 位置
     */
    @NotNull(message = "位置不能为空", groups = Default.class)
    @Length(max = 255, message = "位置不能超过{max}个字符", groups = Default.class)
    @ApiModelProperty(value = "位置")
    private String location;
    /**
     * 支持https `https` tinyint(4) DEFAULT 0  COMMENT 支持https
     */
    @NotNull(message = "支持https不能为空", groups = Default.class)
    @Max(value = Integer.MAX_VALUE, message = "支持https不能超过{value}", groups = Default.class)
    @ApiModelProperty(value = "支持https")
    private Boolean https;
    /**
     * 支持http `http` tinyint(4) DEFAULT 0  COMMENT 支持http
     */
    @NotNull(message = "支持http不能为空", groups = Default.class)
    @Max(value = Integer.MAX_VALUE, message = "支持http不能超过{value}", groups = Default.class)
    @ApiModelProperty(value = "支持http")
    private Boolean http;
    /**
     * 匿名性 `anonymity` tinyint(4) DEFAULT 0  COMMENT 匿名性
     */
    @NotNull(message = "匿名性不能为空", groups = Default.class)
    @Max(value = Integer.MAX_VALUE, message = "匿名性不能超过{value}", groups = Default.class)
    @ApiModelProperty(value = "匿名性")
    private Boolean anonymity;
    /**
     * 可用性 `available` tinyint(4) DEFAULT 0  COMMENT 可用性
     */
    @NotNull(message = "可用性不能为空", groups = Default.class)
    @Max(value = Integer.MAX_VALUE, message = "可用性不能超过{value}", groups = Default.class)
    @ApiModelProperty(value = "可用性")
    private Boolean available;
    /**
     * 最近校验时间 `last_validate_time` datetime  COMMENT 最近校验时间
     */
    @NotNull(message = "最近校验时间不能为空", groups = Default.class)
    @ApiModelProperty(value = "最近校验时间")
    private LocalDateTime lastValidateTime;
    /**
     * 校验次数 `validate_count` int(11)  COMMENT 校验次数
     */
    @NotNull(message = "校验次数不能为空", groups = Default.class)
    @Max(value = Integer.MAX_VALUE, message = "校验次数不能超过{value}", groups = Default.class)
    @ApiModelProperty(value = "校验次数")
    private Integer validateCount;
    /**
     * 校验可用次数 `available_count` int(11)  COMMENT 校验可用次数
     */
    @NotNull(message = "校验可用次数不能为空", groups = Default.class)
    @Max(value = Integer.MAX_VALUE, message = "校验可用次数不能超过{value}", groups = Default.class)
    @ApiModelProperty(value = "校验可用次数")
    private Integer availableCount;
    /**
     * 校验不可用次数 `un_available_count` int(11)  COMMENT 校验不可用次数
     */
    @NotNull(message = "校验不可用次数不能为空", groups = Default.class)
    @Max(value = Integer.MAX_VALUE, message = "校验不可用次数不能超过{value}", groups = Default.class)
    @ApiModelProperty(value = "校验不可用次数")
    private Integer unAvailableCount;
    /**
     * 失效时间 `failure_time` datetime  COMMENT 失效时间
     */
    @NotNull(message = "失效时间不能为空", groups = Default.class)
    @ApiModelProperty(value = "失效时间")
    private LocalDateTime failureTime;
    /**
     * 代理请求需要总时长 `use_times` int(11)  COMMENT 代理请求需要总时长
     */
    @NotNull(message = "代理请求需要总时长不能为空", groups = Default.class)
    @Max(value = Integer.MAX_VALUE, message = "代理请求需要总时长不能超过{value}", groups = Default.class)
    @ApiModelProperty(value = "代理请求需要总时长")
    private Integer useTimes;
    /**
     * 可用率 `available_rate` double(6,4)  COMMENT 可用率
     */
    @NotNull(message = "可用率不能为空", groups = Default.class)
    @ApiModelProperty(value = "可用率")
    private Double availableRate;
    /**
     * 使用次数 `use_size` int(11) unsigned DEFAULT 0  COMMENT 使用次数
     */
    @NotNull(message = "使用次数不能为空", groups = Default.class)
    @Max(value = Integer.MAX_VALUE, message = "使用次数不能超过{value}", groups = Default.class)
    @ApiModelProperty(value = "使用次数")
    private Integer useSize;
    /**
     * 失败次数 `fail_size` int(11) unsigned DEFAULT 0  COMMENT 失败次数
     */
    @NotNull(message = "失败次数不能为空", groups = Default.class)
    @Max(value = Integer.MAX_VALUE, message = "失败次数不能超过{value}", groups = Default.class)
    @ApiModelProperty(value = "失败次数")
    private Integer failSize;
    /**
     * 输入对象转为实体类
     *
     * @return 实体类
     */
    public ProxyIpEntity convertToEntity() {
        ProxyIpEntity entity = new ProxyIpEntity();
        entity.setProxyId(proxyId);
        entity.setIpAddress(ipAddress);
        entity.setIpPort(ipPort);
        entity.setCountry(country);
        entity.setLocation(location);
        entity.setHttps(https);
        entity.setHttp(http);
        entity.setAnonymity(anonymity);
        entity.setAvailable(available);
        entity.setLastValidateTime(lastValidateTime);
        entity.setValidateCount(validateCount);
        entity.setAvailableCount(availableCount);
        entity.setUnAvailableCount(unAvailableCount);
        entity.setFailureTime(failureTime);
        entity.setUseTimes(useTimes);
        entity.setAvailableRate(availableRate);
        entity.setUseSize(useSize);
        entity.setFailSize(failSize);
        return entity;
    }
}