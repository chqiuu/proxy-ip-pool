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
 * 66免费代理 | http://www.66ip.cn/
 */
@Slf4j
public class Ip66ProxyIpDownloader extends ProxyIpDownloader {
    /**
     * 来源
     */
    private static final String PROXY_DOCMAIN = "66ip";
    /**
     * 代理URL模板
     */
    private static final String URL_TEMPLATE = "http://www.66ip.cn/%s.html";
    /**
     * 爬取页数
     */
    private static final int MAX_PAGES = 20;

    public Ip66ProxyIpDownloader() {
        super();
    }

    public Ip66ProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        List<ProxyIp> proxyIps = new ArrayList<>();
        for (int i = 1; i <= MAX_PAGES; i++) {
            String body = null;
            try {
                body = Jsoup.connect(String.format(URL_TEMPLATE, i)).get().html();
                proxyIps.addAll(getProxyIpFromPage(body));
            } catch (IOException e) {
                log.error("{} {}", String.format(URL_TEMPLATE, i), e.getMessage());
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
        Elements ipElements = document.select("div > table > tbody > tr");
        ProxyIp proxyIp = new ProxyIp();
        for (Element rowElement : ipElements) {
            if ("ip".equals(rowElement.selectFirst("td").text().trim())) {
                // 表头跳过
                continue;
            }
            proxyIp = new ProxyIp();
            Elements fieldElements = rowElement.select("td");
            for (int i = 0; i < fieldElements.size(); i++) {
                if (i == 0) {
                    proxyIp.setIpAddress(fieldElements.get(i).text().trim());
                } else if (i == 1) {
                    proxyIp.setIpPort(Integer.valueOf(fieldElements.get(i).text().trim()));
                } else if (i == 3) {
                    // 匿名度
                    proxyIp.setAnonymity("高匿代理".equals(fieldElements.get(i).text().trim()));
                } else if (i == 2) {
                    proxyIp.setLocation(fieldElements.get(i).text().trim());
                }
                proxyIp.setHttp(true);
                proxyIp.setHttps(true);
                proxyIp.setAvailable(true);
            }
            if (proxyIp.getIpPort() != null) {
                proxyIp.setProxyId(String.format("%s:%s", proxyIp.getIpAddress(), proxyIp.getIpPort()));
                proxyIp.setDataSources(PROXY_DOCMAIN);
                proxyIps.add(proxyIp);
            }
        }
        return proxyIps;
    }
}

