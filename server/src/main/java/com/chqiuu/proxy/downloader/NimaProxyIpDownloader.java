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
 * 泥马代理 | http://www.nimadaili.com/
 */
@Slf4j
public class NimaProxyIpDownloader extends ProxyIpDownloader {
    /**
     * 来源
     */
    private static final String PROXY_DOCMAIN = "nimadaili";
    /**
     * 代理URL模板
     */
    private static final String URL_TEMPLATE = "http://www.nimadaili.com/https/%s/";
    /**
     * 爬取页数
     */
    private static final int MAX_PAGES = 100;

    public NimaProxyIpDownloader() {
        super();
    }

    public NimaProxyIpDownloader(String localIp) {
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
        Elements ipElements = document.select("div > div > table > tbody > tr");
        ProxyIp proxyIp = new ProxyIp();
        for (Element rowElement : ipElements) {
            proxyIp = new ProxyIp();
            Elements fieldElements = rowElement.select("td");
            for (int i = 0; i < fieldElements.size(); i++) {
                if (i == 0) {
                    proxyIp.setIpAddress(fieldElements.get(i).text().trim().split(":")[0]);
                    proxyIp.setIpPort(Integer.valueOf(fieldElements.get(i).text().trim().split(":")[1]));
                } else if (i == 1) {
                    String type = fieldElements.get(i).text().trim().toLowerCase();
                    if (type.contains("https")) {
                        proxyIp.setHttps(true);
                        type = type.replaceAll("https", "");
                    }
                    if (type.contains("http")) {
                        proxyIp.setHttp(true);
                    }                } else if (i == 2) {
                    // 匿名度
                    proxyIp.setAnonymity("高匿代理".equals(fieldElements.get(i).text().trim()));
                } else if (i == 3) {
                    proxyIp.setLocation(fieldElements.get(i).text().trim());
                }
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

