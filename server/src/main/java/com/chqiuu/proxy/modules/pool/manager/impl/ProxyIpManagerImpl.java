package com.chqiuu.proxy.modules.pool.manager.impl;

import cn.hutool.core.util.StrUtil;
import com.chqiuu.proxy.common.util.NetworkUtil;
import com.chqiuu.proxy.config.ProxyProperties;
import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;
import com.chqiuu.proxy.modules.pool.manager.ProxyIpManager;
import com.chqiuu.proxy.modules.pool.mapper.ProxyIpMapper;
import lombok.AllArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.core5.http.HttpHost;
import org.jsoup.Jsoup;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.concurrent.Future;

@Slf4j
@Service
@AllArgsConstructor
public class ProxyIpManagerImpl implements ProxyIpManager {

    private final ProxyIpMapper proxyIpMapper;
    private final ProxyProperties proxyProperties;

    @Async("validateProxyAsyncExecutor")
    @Override
    public Future<Integer> validateProxyIp(ProxyIpEntity entity, List<String> testUrls) {
        int validateCount = testUrls.size();
        int availableCount = 0;
        int unAvailableCount = 0;
        long startTime = System.currentTimeMillis(), endTime = 0L;
        long requestStartTime = 0, requestEndTime = 0, useTimes = entity.getUseTimes();
        for (String url : testUrls) {
            requestStartTime = System.currentTimeMillis();
            HttpHost proxy = new HttpHost(entity.getIpAddress(), entity.getIpPort());
            String html = NetworkUtil.get(url, proxyProperties.getLocalIp(), proxy, 2000);
            if (StrUtil.isNotEmpty(html) && StrUtil.isNotEmpty(Jsoup.parse(html).select("#articleContentId").text())) {
                // 代理IP连接成功
                availableCount++;
                requestEndTime = System.currentTimeMillis();
                useTimes = useTimes + (requestEndTime - requestStartTime);
            } else {
                unAvailableCount++;
            }
        }
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
        if (validateCount > 0 && validateCount == unAvailableCount) {
            updateEntity.setAvailable(false);
            updateEntity.setFailureTime(LocalDateTime.now());
        }
        updateEntity.setLastValidateTime(LocalDateTime.now());
        this.proxyIpMapper.updateById(updateEntity);
        endTime = System.currentTimeMillis();
        log.info("{}, {} = {} + {}, TimeMillis={} ", entity.getProxyId(), validateCount, availableCount, unAvailableCount, endTime - startTime);
        return new AsyncResult<>(availableCount);
    }

    @SneakyThrows
    @Async("timeoutAsyncExecutor")
    @Override
    public void checkFuture(String proxyId, long startTime, Future<Integer> future) {
        // 不断更新，直到任务处理完毕或者超时
        while (true) {
            long endTime = System.currentTimeMillis();
            // 如果异步方法执行完了，在退出循环
            if (future.isDone()) {
                log.info("任务执行完成 {} {}", proxyId, endTime - startTime);
                break;
            } else if (endTime - startTime >= 13000) {
                // 超过6秒种了，任务超时被取消
                future.cancel(true);
                log.info("超过13秒了，任务超时被取消 {}", proxyId);
                break;
            }
        }
    }
}
