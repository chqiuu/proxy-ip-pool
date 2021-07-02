package com.chqiuu.proxy.task;

import com.chqiuu.proxy.modules.pool.service.ProxyIpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/**
 * 系统定时任务
 * <p>
 * 1. 定时下载最新的代理IP任务
 * 2. 定时监测新增代理IP的有效性任务
 * 3. 定时监测当前可使用的代理IP有效性任务
 * 4. 定时监测当前不可使用的代理IP有效性任务
 *
 * @author chqiu
 */
@Slf4j
@Component
@AllArgsConstructor
public class ProxyScheduledTask {

    private final ProxyIpService proxyIpService;

    /**
     * 定时下载最新的代理IP任务
     * <p>
     * 执行频率，每天更新一次
     */
   @Scheduled(cron = "0 50 8 * * ?")
   // @Scheduled(initialDelay = 1000, fixedDelay = 21600000)
    public void batchUpdateProxyIpTask() {
        log.info("开始执行下载最新的代理IP任务");
        proxyIpService.batchUpdateProxyIp();
        log.info("[执行结束]下载最新的代理IP任务");
    }

    /**
     * 定时监测新增代理IP的有效性任务
     * <p>
     * 执行频率，每次启动项目后固定频率执行 5分钟
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 300000)
    public void batchValidateNewsProxyIpTask() {
        log.info("开始执行监测新增代理IP的有效性任务");
        proxyIpService.batchValidateNewsProxyIp();
        log.info("[执行结束]监测新增代理IP的有效性任务");
    }

    /**
     * 定时监测当前可使用的代理IP有效性任务
     * <p>
     * 执行频率，每次启动项目后固定频率执行 1小时
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 3600000)
    public void batchValidateAvailableProxyIpTask() {
        log.info("开始执行监测当前可使用的代理IP有效性任务");
        proxyIpService.batchValidateAvailableProxyIp();
        log.info("[执行结束]监测当前可使用的代理IP有效性任务");
    }

    /**
     * 定时监测当前不可使用的代理IP有效性任务
     * <p>
     * 执行频率，每次启动项目后固定频率执行 6小时
     */
    @Scheduled(initialDelay = 1000, fixedDelay = 21600000)
    public void batchValidateUnavailableProxyIpTask() {
        log.info("开始执行监测当前不可使用的代理IP有效性任务");
        proxyIpService.batchValidateUnavailableProxyIp();
        log.info("[执行结束]监测当前不可使用的代理IP有效性任务");
    }
}
