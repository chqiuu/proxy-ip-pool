package com.chqiuu.proxy.modules.pool.manager;

import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;

import java.util.List;
import java.util.concurrent.Future;

public interface ProxyIpManager {
    /**
     * 校验代理IP
     *
     * @param entity   实体类
     * @param testUrls 用于测试的URL列表
     * @return 返回可用数
     */
    Future<Integer> validateProxyIp(ProxyIpEntity entity, List<String> testUrls);

    void checkFuture(String proxyId, long startTime, Future<Integer> future);
}
