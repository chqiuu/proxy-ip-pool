package com.chqiuu.proxy.downloader;

import cn.hutool.core.util.StrUtil;
import com.chqiuu.proxy.common.util.NetworkUtil;
import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.util.ArrayList;
import java.util.List;

/**
 * 89免费代理 | http://www.89ip.cn/
 */
@Slf4j
public class Ip89ProxyIpDownloader extends ProxyIpDownloader {
    /**
     * 来源
     */
    private static final String PROXY_DOCMAIN = "89ip";
    /**
     * 代理URL模板
     */
    private static final String URL_TEMPLATE = "https://www.89ip.cn/index_%s.html";
    /**
     * 爬取页数
     */
    private static final int MAX_PAGES = 30;

    public Ip89ProxyIpDownloader() {
        super();
    }

    public Ip89ProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        List<ProxyIp> proxyIps = new ArrayList<>();
        for (int i = 1; i <= MAX_PAGES; i++) {
            proxyIps.addAll(getProxyIpFromPage(NetworkUtil.get(String.format(URL_TEMPLATE, i), this.localIp)));
        }
        log.info("downloadProxyIps {} Size {}", PROXY_DOCMAIN, proxyIps.size());
        return proxyIps;
    }

    private List<ProxyIp> getProxyIpFromPage(String body) {
        List<ProxyIp> proxyIps = new ArrayList<>();
        if (null == body) {
            return proxyIps;
        }
        Document document = Jsoup.parse(body);
        Elements ipElements = document.select("div.layui-form > table > tbody > tr");
        ProxyIp proxyIp = new ProxyIp();
        for (Element rowElement : ipElements) {
            proxyIp = new ProxyIp();
            Elements fieldElements = rowElement.select("td");
            for (int i = 0; i < fieldElements.size(); i++) {
                if (i == 0) {
                    proxyIp.setIpAddress(fieldElements.get(i).text().trim());
                } else if (i == 1) {
                    proxyIp.setIpPort(Integer.valueOf(fieldElements.get(i).text().trim()));
                } else if (i == 2) {
                    proxyIp.setLocation(fieldElements.get(i).text().trim());
                }
                proxyIp.setAnonymity(true);
                proxyIp.setHttp(true);
                proxyIp.setHttps(true);
                proxyIp.setAvailable(true);
            }
            if (StrUtil.isNotEmpty(proxyIp.getIpAddress()) && proxyIp.getIpPort() != null) {
                proxyIp.setProxyId(String.format("%s:%s", proxyIp.getIpAddress(), proxyIp.getIpPort()));
                proxyIp.setDataSources(PROXY_DOCMAIN);
                proxyIps.add(proxyIp);
            }
        }
        return proxyIps;
    }
}

