package com.chqiuu.proxy.runner;

import com.chqiuu.proxy.config.ProxyProperties;
import com.chqiuu.proxy.downloader.*;
import com.chqiuu.proxy.modules.pool.service.ProxyIpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

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
        // proxyIpService.saveBatchProxyIp(new FeizhuProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        proxyIpService.saveBatchProxyIp(new IhuanProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        proxyIpService.saveBatchProxyIp(new Ip66ProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        // proxyIpService.saveBatchProxyIp(new KuaidailiProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        // proxyIpService.saveBatchProxyIp(new ProxyListPlusProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        // proxyIpService.saveBatchProxyIp(new ShenjidailiProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        proxyIpService.validateBatch();
    }
}
