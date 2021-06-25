package com.chqiuu.proxy.runner;

import com.chqiuu.proxy.config.ProxyProperties;
import com.chqiuu.proxy.downloader.ProxyIpDownloader;
import com.chqiuu.proxy.downloader.ShenjidailiProxyIpDownloader;
import com.chqiuu.proxy.downloader.model.ProxyIp;
import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;
import com.chqiuu.proxy.modules.pool.service.ProxyIpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

/**
 * @author chqiu
 */
@Slf4j
@Component
@AllArgsConstructor
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final ProxyIpService proxyIpService;
    private final ProxyProperties proxyProperties;

    @Override
    public void run(String... args) throws Exception {
        log.info("启动时自动执行 CommandLineRunner 的 run 方法");
        ProxyIpDownloader proxyIpDownloader = new ShenjidailiProxyIpDownloader(proxyProperties.getLocalIp());
        List<ProxyIp> proxyIps = proxyIpDownloader.downloadProxyIps();
        List<ProxyIpEntity> entities = new ArrayList<>();
        proxyIps.forEach(proxyIp -> entities.add(proxyIp.convertToEntity()));
        proxyIpService.saveBatch(entities);
        proxyIpService.validateBatch();
    }
}
