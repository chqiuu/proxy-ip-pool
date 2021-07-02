package com.chqiuu.proxy.modules.pool.manager;

import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;

import java.util.List;

/**
 * @author chqiu
 */
public interface ProxyIpManager {
    /**
     * 校验代理IP
     *
     * @param entity   实体类
     * @param testUrls 用于测试的URL列表
     */
    void validateProxyIp(ProxyIpEntity entity, List<String> testUrls);

    /**
     * 校验新的代理IP
     *
     * @param entity   实体类
     * @param testUrls 用于测试的URL列表
     */
    void validateNewsProxyIp(ProxyIpEntity entity, List<String> testUrls);

    /**
     * 校验当前可用的代理IP
     *
     * @param entity   实体类
     * @param testUrls 用于测试的URL列表
     */
    void validateAvailableProxyIp(ProxyIpEntity entity, List<String> testUrls);

    /**
     * 校验当前不可用的代理IP
     *
     * @param entity   实体类
     * @param testUrls 用于测试的URL列表
     */
    void validateUnavailableProxyIp(ProxyIpEntity entity, List<String> testUrls);
}
