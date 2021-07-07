package com.chqiuu.proxy.modules.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;
import java.time.LocalDateTime;

@Data
@ApiModel(value = "代理IP列表信息API")
public class ProxyIpCommonListDTO implements Serializable {

    private static final long serialVersionUID = 1L;
    /**
     * 唯一ID `proxy_id` varchar(30)  COMMENT 唯一ID
     */
    @ApiModelProperty(value = "唯一ID")
    private String proxyId;
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
     * 位置 `location` varchar(255)  COMMENT 位置
     */
    @ApiModelProperty(value = "位置")
    private String location;
    /**
     * 支持https `https` tinyint(4) DEFAULT 0  COMMENT 支持https
     */
    @ApiModelProperty(value = "支持https")
    private Boolean https;
    /**
     * 支持http `http` tinyint(4) DEFAULT 0  COMMENT 支持http
     */
    @ApiModelProperty(value = "支持http")
    private Boolean http;
    /**
     * 匿名性 `anonymity` tinyint(4) DEFAULT 0  COMMENT 匿名性
     */
    @ApiModelProperty(value = "匿名性")
    private Boolean anonymity;
    /**
     * 最近校验时间 `last_validate_time` datetime  COMMENT 最近校验时间
     */
    @ApiModelProperty(value = "最近校验时间")
    private LocalDateTime lastValidateTime;
    /**
     * 创建时间 `create_time` datetime DEFAULT CURRENT_TIMESTAMP  COMMENT 创建时间
     */
    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;
}
