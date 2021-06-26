package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * 云代理 | http://www.ip3366.net/
 */
@Slf4j
public class Ip3366ProxyIpDownloader extends ProxyIpDownloader {
    /**
     * 来源
     */
    private static final String PROXY_DOCMAIN = "ip3366";
    /**
     * 高匿代理URL模板
     */
    private static final String INHA_URL_TEMPLATE = "http://www.ip3366.net/free/?stype=1&page=%s";
    /**
     * 普通代理URL模板
     */
    private static final String INTR_URL_TEMPLATE = "http://www.ip3366.net/free/?stype=2&page=%s";
    /**
     * 爬取页数
     */
    private static final int MAX_PAGES = 10;

    public Ip3366ProxyIpDownloader() {
        super();
    }

    public Ip3366ProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        List<ProxyIp> proxyIps = new ArrayList<>();
        for (int i = 1; i <= MAX_PAGES; i++) {
            try {
                proxyIps.addAll(getProxyIpFromPage(Jsoup.connect(String.format(INHA_URL_TEMPLATE, i)).get().html()));
            } catch (IOException e) {
                log.error("{} {}", String.format(INHA_URL_TEMPLATE, i), e.getMessage());
            }
            try {
                proxyIps.addAll(getProxyIpFromPage(Jsoup.connect(String.format(INTR_URL_TEMPLATE, i)).get().html()));
            } catch (IOException e) {
                log.error("{} {}", String.format(INTR_URL_TEMPLATE, i), e.getMessage());
            }
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
        Elements ipElements = document.select("#list > table > tbody > tr");
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
                    // 匿名度
                    proxyIp.setAnonymity("高匿代理IP".equals(fieldElements.get(i).text().trim()));
                } else if (i == 3) {
                    String type = fieldElements.get(i).text().trim().toLowerCase();
                    if (type.contains("https")) {
                        proxyIp.setHttps(true);
                        type = type.replaceAll("https", "");
                    }
                    if (type.contains("http")) {
                        proxyIp.setHttp(true);
                    }
                } else if (i == 4) {
                    proxyIp.setLocation(fieldElements.get(i).text().trim());
                }
                proxyIp.setAvailable(true);
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

