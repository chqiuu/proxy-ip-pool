package com.chqiuu.proxy.modules.pool.manager;

import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;

import java.util.List;

public interface ProxyIpManager {
    /**
     * 校验代理IP
     *
     * @param entity   实体类
     * @param testUrls 用于测试的URL列表
     */
    void validateProxyIp(ProxyIpEntity entity, List<String> testUrls);
}
