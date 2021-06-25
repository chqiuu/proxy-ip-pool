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
 * 飞猪IP代理 | https://www.feizhuip.com/News-newsList-catid-8.html
 */
@Slf4j
public class FeizhuProxyIpDownloader extends ProxyIpDownloader {

    /**
     * URL
     */
    private static final String LIST_URL = "https://www.feizhuip.com/News-newsList-catid-8.html";
    /**
     * DOMAIN_URL
     */
    private static final String DOMAIN_URL = "https://www.feizhuip.com";

    /**
     * 爬取页数
     */
    private static final int MAX_PAGES = 16;

    public FeizhuProxyIpDownloader() {
        super();
    }

    public FeizhuProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        List<ProxyIp> proxyIps = new ArrayList<>();
        String listHtml = NetworkUtil.get(LIST_URL, this.localIp);
        if (StrUtil.isNotEmpty(listHtml)) {
            Document document = Jsoup.parse(listHtml);
            Elements urlElements = document.select("body > div.news-body.clearfix > div > div.news-container > div.news-l.fl > ul > li > a");
            for (int i = 0; i < urlElements.size(); i++) {
                if (i > MAX_PAGES) {
                    break;
                }
                proxyIps.addAll(getProxyIpFromPage(NetworkUtil.get(String.format("%s%s", DOMAIN_URL, urlElements.get(i).attr("href")), this.localIp)));
            }
        }
        return proxyIps;
    }

    private List<ProxyIp> getProxyIpFromPage(String body) {
        List<ProxyIp> proxyIps = new ArrayList<>();
        if (null == body) {
            return proxyIps;
        }
        Document document = Jsoup.parse(body);
        Elements ipElements = document.select("div.news-d-con > div > table > tbody > tr");
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
                    proxyIp.setCountry("中国");
                    proxyIp.setLocation(fieldElements.get(i).text().trim());
                }
                proxyIp.setHttps(true);
                proxyIp.setHttp(true);
                proxyIp.setAnonymity(true);
                proxyIp.setAvailable(true);
            }
            if (proxyIp.getIpAddress() != null) {
                proxyIp.setProxyId(String.format("%s:%s", proxyIp.getIpAddress(), proxyIp.getIpPort()));
                proxyIp.setDataSources("feizhuip");
                proxyIps.add(proxyIp);
            }
        }
        return proxyIps;
    }
}
