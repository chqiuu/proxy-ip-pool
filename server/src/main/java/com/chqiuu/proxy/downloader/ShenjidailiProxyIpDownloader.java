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
 * |神鸡代理 | http://www.shenjidaili.com/product/open/
 *
 * @author chqiu
 */
@Slf4j
public class ShenjidailiProxyIpDownloader extends ProxyIpDownloader {
    /**
     * 来源
     */
    private static final String PROXY_DOCMAIN = "shenjidaili";

    private static final String URL = "http://www.shenjidaili.com/product/open/";

    public ShenjidailiProxyIpDownloader() {
        super();
    }

    public ShenjidailiProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        List<ProxyIp> proxyIps = new ArrayList<>();
        String body = NetworkUtil.get(URL, this.localIp);
        if (null == body) {
            return null;
        }
        Document document = Jsoup.parse(body);
        Elements ipElements = document.select("#pills-tabContent table tbody tr");
        ProxyIp proxyIp = new ProxyIp();
        for (Element rowElement : ipElements) {
            if ("免费ip".equals(rowElement.selectFirst("td").text().trim())) {
                // 表头跳过
                continue;
            }
            proxyIp = new ProxyIp();
            Elements fieldElements = rowElement.select("td");
            for (int i = 0; i < fieldElements.size(); i++) {
                if (i == 0) {
                    proxyIp.setIpAddress(fieldElements.get(i).text().trim().split(":")[0]);
                } else if (i == 1) {
                    proxyIp.setIpPort(Integer.valueOf(fieldElements.get(i).text().trim()));
                } else if (i == 2) {
                    // 匿名度
                    if ("高匿".equals(fieldElements.get(i).text().trim())) {
                        proxyIp.setAnonymity(true);
                    } else {
                        proxyIp.setAnonymity(false);
                    }
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
                    proxyIp.setCountry(fieldElements.get(i).text().trim().split(" ")[0]);
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
        log.info("downloadProxyIps {} Size {}", PROXY_DOCMAIN, proxyIps.size());
        return proxyIps;
    }
}

