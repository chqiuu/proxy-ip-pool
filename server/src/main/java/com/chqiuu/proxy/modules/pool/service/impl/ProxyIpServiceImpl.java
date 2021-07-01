package com.chqiuu.proxy.modules.pool.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chqiuu.proxy.common.util.NetworkUtil;
import com.chqiuu.proxy.config.ProxyProperties;
import com.chqiuu.proxy.downloader.*;
import com.chqiuu.proxy.downloader.model.ProxyIp;
import com.chqiuu.proxy.modules.pool.dto.ProxyIpDetailDTO;
import com.chqiuu.proxy.modules.pool.dto.ProxyIpListDTO;
import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;
import com.chqiuu.proxy.modules.pool.manager.ProxyIpManager;
import com.chqiuu.proxy.modules.pool.mapper.ProxyIpMapper;
import com.chqiuu.proxy.modules.pool.query.ProxyIpListQuery;
import com.chqiuu.proxy.modules.pool.query.ProxyIpPageQuery;
import com.chqiuu.proxy.modules.pool.service.ProxyIpService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.Future;

/**
 * 代理IP业务逻辑层实现
 *
 * @author chqiuu
 * @date 2021年6月11日
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProxyIpServiceImpl extends ServiceImpl<ProxyIpMapper, ProxyIpEntity> implements ProxyIpService {
    private final ProxyProperties proxyProperties;
    private final ProxyIpManager proxyIpManager;
    private final ThreadPoolTaskExecutor validateProxyAsyncExecutor;
    private final static int MAX_TEST_URL_COUNT = 6;

    @Override
    public ProxyIpDetailDTO getDetailById(String proxyId) {
        return this.baseMapper.getDetailById(proxyId);
    }

    @Override
    public List<ProxyIpListDTO> getList(ProxyIpListQuery query) {
        return this.baseMapper.getList(query);
    }

    @Override
    public IPage<ProxyIpListDTO> getPage(ProxyIpPageQuery query) {
        Page<ProxyIpListDTO> pageInfo = new Page<>(query.getCurrent(), query.getSize());
        return this.baseMapper.getPage(pageInfo, query);
    }

    @Override
    public void saveBatchProxyIp() {
        this.saveBatchProxyIp(new FeizhuProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        this.saveBatchProxyIp(new IhuanProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        this.saveBatchProxyIp(new Ip66ProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        this.saveBatchProxyIp(new Ip89ProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        this.saveBatchProxyIp(new Ip3366ProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        this.saveBatchProxyIp(new KuaidailiProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        this.saveBatchProxyIp(new KxProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        this.saveBatchProxyIp(new NimaProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        this.saveBatchProxyIp(new ProxyListPlusProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        this.saveBatchProxyIp(new QiyunProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        this.saveBatchProxyIp(new ShenjidailiProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        this.saveBatchProxyIp(new SuperFastProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        this.saveBatchProxyIp(new TaiyangProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
        this.saveBatchProxyIp(new XiaosuProxyIpDownloader(proxyProperties.getLocalIp()).downloadProxyIps());
    }

    @Override
    public void saveBatchProxyIp(List<ProxyIp> proxyIps) {
        proxyIps.forEach(proxyIp -> this.baseMapper.insertIgnore(proxyIp.convertToEntity()));

    }

    @Override
    public void validateBatch() {
        validateBatch(this.baseMapper.getValidateProxys(MAX_TEST_URL_COUNT));
    }

    @Override
    public void validateBatch(List<ProxyIpEntity> entities) {
        List<String> testUrls = getTestUrls();
        entities.forEach(proxyIpEntity -> {
            // 打乱顺序
            Collections.shuffle(testUrls);
            while (true) {
                // 当前活动线程数
                int activeCount = validateProxyAsyncExecutor.getThreadPoolExecutor().getActiveCount();
                if (activeCount < 16) {
                    break;
                }
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
            // 获取第一次进入的时间
            long startTime = System.currentTimeMillis();
            log.info("开始任务 {}", proxyIpEntity.getProxyId());
            Future<Integer> future = proxyIpManager.validateProxyIp(proxyIpEntity, testUrls.size() > MAX_TEST_URL_COUNT ? testUrls.subList(0, MAX_TEST_URL_COUNT) : testUrls);
            proxyIpManager.checkFuture(proxyIpEntity.getProxyId(), startTime, future);
        });
    }

    /**
     * 获取测试URL列表
     *
     * @return
     */
    private List<String> getTestUrls() {
        List<String> urls = new ArrayList<>();
        String body = NetworkUtil.get("https://blog.csdn.net/QIU176161650/article/list", proxyProperties.getLocalIp());
        Document document = Jsoup.parse(body);
        Elements articleElements = document.select("div.article-list > div > h4 > a");
        for (Element articleElement : articleElements) {
            urls.add(articleElement.attr("href"));
        }
        return urls;
    }
}