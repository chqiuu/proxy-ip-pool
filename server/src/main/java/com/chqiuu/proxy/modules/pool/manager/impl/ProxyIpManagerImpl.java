package com.chqiuu.proxy.modules.pool.manager.impl;

import cn.hutool.core.util.StrUtil;
import com.chqiuu.proxy.common.util.NetworkUtil;
import com.chqiuu.proxy.config.ProxyProperties;
import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;
import com.chqiuu.proxy.modules.pool.manager.ProxyIpManager;
import com.chqiuu.proxy.modules.pool.mapper.ProxyIpMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@AllArgsConstructor
public class ProxyIpManagerImpl implements ProxyIpManager {

    private final ProxyIpMapper proxyIpMapper;
    private final ProxyProperties proxyProperties;

    @Async
    @Override
    public void validateProxyIp(ProxyIpEntity entity, List<String> testUrls) {
        int validateCount = testUrls.size();
        int availableCount = 0;
        int unAvailableCount = 0;
        long startTime = 0, endTime = 0, useTimes = entity.getUseTimes();
        for (String url : testUrls) {
            startTime = System.currentTimeMillis();
            HttpHost proxy = new HttpHost(entity.getIpAddress(), entity.getIpPort());
            if (StrUtil.isNotEmpty(NetworkUtil.get(url, proxyProperties.getLocalIp(), proxy, 2000))) {
                // 代理IP连接成功
                availableCount++;
                endTime = System.currentTimeMillis();
                useTimes = useTimes + (endTime - startTime);
            } else {
                unAvailableCount++;
            }
        }
        log.info("{}, {} = {} + {}", entity.getProxyId(), validateCount, availableCount, unAvailableCount);
        ProxyIpEntity updateEntity = new ProxyIpEntity();
        updateEntity.setProxyId(entity.getProxyId());
        updateEntity.setValidateCount(entity.getValidateCount() + validateCount);
        updateEntity.setAvailableCount(entity.getAvailableCount() + availableCount);
        updateEntity.setUnAvailableCount(entity.getUnAvailableCount() + unAvailableCount);
        if (updateEntity.getValidateCount() > 0) {
            updateEntity.setAvailableRate(updateEntity.getAvailableCount() * 1.00 / updateEntity.getValidateCount());
        }
        updateEntity.setUseTimes(useTimes);
        if (updateEntity.getAvailableCount() > 0) {
            updateEntity.setAvgUseTimes(useTimes / updateEntity.getAvailableCount());
        }
        updateEntity.setLastValidateTime(LocalDateTime.now());
        this.proxyIpMapper.updateById(updateEntity);
    }
}
