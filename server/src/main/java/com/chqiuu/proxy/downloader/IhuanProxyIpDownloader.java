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
 * 小幻HTTP代理 | https://ip.ihuan.me/
 */
@Slf4j
public class IhuanProxyIpDownloader extends ProxyIpDownloader {
    /**
     * 来源
     */
    private static final String PROXY_DOCMAIN = "ihuan";
    /**
     * DOMAIN_URL
     */
    private static final String DOMAIN_URL = "https://ip.ihuan.me";
    /**
     * 爬取页数
     */
    private static final int MAX_PAGES = 100;
    /**
     * 当前已抓取页数
     */
    private int nowPages = 0;

    public IhuanProxyIpDownloader() {
        super();
    }

    public IhuanProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        List<ProxyIp> proxyIps = new ArrayList<>();
        String body = NetworkUtil.get(String.format("%s/?page=1", DOMAIN_URL), this.localIp);
        if (StrUtil.isNotEmpty(body)) {
            proxyIps.addAll(getProxyIpFromPage(proxyIps, body));
        }
        log.info("downloadProxyIps {} Size {}", PROXY_DOCMAIN, proxyIps.size());
        return proxyIps;
    }

    private List<ProxyIp> getProxyIpFromPage(List<ProxyIp> proxyIps, String body) {
        Document document = Jsoup.parse(body);
        proxyIps.addAll(getProxyIpFromPage(document));
        if (nowPages < MAX_PAGES) {
            nowPages++;
            String nextPageUrl = document.select("div.col-md-10 > nav > ul > li:nth-child(3) > a").attr("href");
            getProxyIpFromPage(proxyIps, NetworkUtil.get(String.format("%s/%s", DOMAIN_URL, nextPageUrl), this.localIp));
        }
        return proxyIps;
    }

    private List<ProxyIp> getProxyIpFromPage(Document document) {
        List<ProxyIp> proxyIps = new ArrayList<>();
        if (null == document) {
            return proxyIps;
        }
        Elements ipElements = document.select("div.table-responsive > table > tbody > tr");
        ProxyIp proxyIp;
        for (Element rowElement : ipElements) {
            proxyIp = new ProxyIp();
            Elements fieldElements = rowElement.select("td");
            for (int i = 0; i < fieldElements.size(); i++) {
                try {
                    if (i == 0) {
                        proxyIp.setIpAddress(fieldElements.get(i).text().trim());
                    } else if (i == 1) {
                        proxyIp.setIpPort(Integer.valueOf(fieldElements.get(i).text().trim()));
                    } else if (i == 2) {
                        proxyIp.setCountry(fieldElements.get(i).text().trim().split(" ")[0]);
                        proxyIp.setLocation(fieldElements.get(i).text().trim());
                    } else if (i == 4) {
                        if ("支持".equals(fieldElements.get(i).text().trim())) {
                            proxyIp.setHttps(true);
                        }
                    } else if (i == 6) {
                        // 匿名度
                        if ("高匿".equals(fieldElements.get(i).text().trim())) {
                            proxyIp.setAnonymity(true);
                        } else {
                            proxyIp.setAnonymity(false);
                        }
                    }
                    proxyIp.setHttp(true);
                    proxyIp.setAvailable(true);
                } catch (NumberFormatException ignored) {
                }
            }
            if (proxyIp.getIpAddress() != null) {
                proxyIp.setProxyId(String.format("%s:%s", proxyIp.getIpAddress(), proxyIp.getIpPort()));
                proxyIp.setDataSources(PROXY_DOCMAIN);
                proxyIps.add(proxyIp);
            }
        }
        return proxyIps;
    }
}
