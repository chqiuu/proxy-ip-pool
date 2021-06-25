package com.chqiuu.proxy.downloader.model;

import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.io.Serializable;

/**
 * @author chqiu
 */
@Data
public class ProxyIp implements Serializable {
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
     * 数据来源 `data_sources` varchar(255)  COMMENT 数据来源
     */
    @ApiModelProperty(value = "数据来源")
    private String dataSources;
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
     * 可用性 `available` tinyint(4) DEFAULT 0  COMMENT 可用性
     */
    @ApiModelProperty(value = "可用性")
    private Boolean available;

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
        entity.setDataSources(dataSources);
        entity.setCountry(country);
        entity.setLocation(location);
        entity.setHttps(https);
        entity.setHttp(http);
        entity.setAnonymity(anonymity);
        entity.setAvailable(available);
        return entity;
    }
}
