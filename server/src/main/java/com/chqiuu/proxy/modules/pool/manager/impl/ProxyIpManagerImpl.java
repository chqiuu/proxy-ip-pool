package com.chqiuu.proxy.modules.pool.manager.impl;

import cn.hutool.core.util.StrUtil;
import com.chqiuu.proxy.common.util.NetworkUtil;
import com.chqiuu.proxy.config.ProxyProperties;
import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;
import com.chqiuu.proxy.modules.pool.manager.ProxyIpManager;
import com.chqiuu.proxy.modules.pool.mapper.ProxyIpMapper;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpHost;
import org.jsoup.Jsoup;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

/**
 * @author chqiu
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProxyIpManagerImpl implements ProxyIpManager {
    private final ProxyProperties proxyProperties;
    private final ProxyIpMapper proxyIpMapper;

    @Async("validateNewsProxyIpAsyncExecutor")
    @Override
    public void validateNewsProxyIp(ProxyIpEntity entity, List<String> testUrls) {
        this.validateProxyIp(entity, testUrls);
    }

    @Async("validateAvailableProxyIpAsyncExecutor")
    @Override
    public void validateAvailableProxyIp(ProxyIpEntity entity, List<String> testUrls) {
        this.validateProxyIp(entity, testUrls);
    }

    @Async("validateUnavailableProxyIpAsyncExecutor")
    @Override
    public void validateUnavailableProxyIp(ProxyIpEntity entity, List<String> testUrls) {
        this.validateProxyIp(entity, testUrls);
    }

    @Override
    public void validateProxyIp(ProxyIpEntity entity, List<String> testUrls) {
        int validateCount = 0;
        int availableCount = 0;
        int unavailableCount = 0;
        long startTime = System.currentTimeMillis(), endTime = 0L;
        long requestStartTime = 0, requestEndTime = 0, useTimes = entity.getUseTimes();
        for (validateCount = 0; validateCount < testUrls.size(); validateCount++) {
            // 单次请求开始时间
            requestStartTime = System.currentTimeMillis();
            HttpHost proxy = new HttpHost(entity.getIpAddress(), entity.getIpPort());
            String html = NetworkUtil.get(testUrls.get(validateCount), proxyProperties.getLocalIp(), proxy, proxyProperties.getRequestTimeout());
            // 单次请求结束时间
            requestEndTime = System.currentTimeMillis();
            if (StrUtil.isNotEmpty(html) && StrUtil.isNotEmpty(Jsoup.parse(html).select("#articleContentId").text())) {
                // 代理IP连接成功
                availableCount++;
                useTimes = useTimes + (requestEndTime - requestStartTime);
            } else {
                unavailableCount++;
                validateCount++;
                // 连接失败直接退出
                break;
            }
        }
        ProxyIpEntity updateEntity = new ProxyIpEntity();
        updateEntity.setProxyId(entity.getProxyId());
        updateEntity.setValidateCount(entity.getValidateCount() + validateCount);
        updateEntity.setAvailableCount(entity.getAvailableCount() + availableCount);
        updateEntity.setUnavailableCount(entity.getUnavailableCount() + unavailableCount);
        if (updateEntity.getValidateCount() > 0) {
            updateEntity.setAvailableRate(updateEntity.getAvailableCount() * 1.00 / updateEntity.getValidateCount());
        }
        updateEntity.setUseTimes(useTimes);
        if (updateEntity.getAvailableCount() > 0) {
            updateEntity.setAvgUseTimes(useTimes / updateEntity.getAvailableCount());
        }
        if (validateCount > 0 && validateCount <= unavailableCount) {
            updateEntity.setAvailable(false);
            updateEntity.setFailureTime(LocalDateTime.now());
        } else {
            updateEntity.setAvailable(true);
        }
        updateEntity.setLastValidateTime(LocalDateTime.now());
        this.proxyIpMapper.updateById(updateEntity);
        endTime = System.currentTimeMillis();
        log.debug("{}, {} = {} + {}, TimeMillis={} ", entity.getProxyId(), validateCount, availableCount, unavailableCount, endTime - startTime);
    }

}
