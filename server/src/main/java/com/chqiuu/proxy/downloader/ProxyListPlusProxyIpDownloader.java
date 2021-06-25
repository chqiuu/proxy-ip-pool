package com.chqiuu.proxy.downloader;

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
 * ProxyList+ | https://list.proxylistplus.com/
 */
@Slf4j
public class ProxyListPlusProxyIpDownloader extends ProxyIpDownloader {

    /**
     * 高匿代理URL模板
     */
    private static final String INHA_URL_TEMPLATE = "https://list.proxylistplus.com/SSL-List-%s";
    /**
     * 普通代理URL模板
     */
    private static final String INTR_URL_TEMPLATE = "https://list.proxylistplus.com/Fresh-HTTP-Proxy-List-%s";

    /**
     * 爬取页数
     */
    private static final int MAX_PAGES = 6;

    public ProxyListPlusProxyIpDownloader() {
        super();
    }

    public ProxyListPlusProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        List<ProxyIp> proxyIps = new ArrayList<>();
        for (int i = 1; i <= MAX_PAGES; i++) {
            proxyIps.addAll(getProxyIpFromPage(NetworkUtil.get(String.format(INHA_URL_TEMPLATE, i), this.localIp)));
            proxyIps.addAll(getProxyIpFromPage(NetworkUtil.get(String.format(INTR_URL_TEMPLATE, i), this.localIp)));
        }
        return proxyIps;
    }

    private List<ProxyIp> getProxyIpFromPage(String body) {
        List<ProxyIp> proxyIps = new ArrayList<>();
        if (null == body) {
            return proxyIps;
        }
        Document document = Jsoup.parse(body);
        Elements ipElements = document.select("#page > table.bg > tbody > tr");
        ProxyIp proxyIp = new ProxyIp();
        for (Element rowElement : ipElements) {
            try {
                proxyIp = new ProxyIp();
                Elements fieldElements = rowElement.select("td");
                for (int i = 0; i < fieldElements.size(); i++) {
                    if (i == 1) {
                        proxyIp.setIpAddress(fieldElements.get(i).text().trim());
                    } else if (i == 2) {
                        proxyIp.setIpPort(Integer.valueOf(fieldElements.get(i).text().trim()));
                    } else if (i == 3) {
                        // 匿名度
                        if ("anonymous".equals(fieldElements.get(i).text().trim())) {
                            proxyIp.setAnonymity(true);
                        } else {
                            proxyIp.setAnonymity(false);
                        }
                    } else if (i == 4) {
                        proxyIp.setCountry(fieldElements.get(i).text().trim());
                        proxyIp.setLocation(fieldElements.get(i).text().trim());
                    } else if (i == 6) {
                        if ("yes".equals(fieldElements.get(i).text().trim())) {
                            proxyIp.setHttps(true);
                        }
                        proxyIp.setHttp(true);
                    }
                    proxyIp.setAvailable(true);
                }
                if (proxyIp.getIpAddress() != null) {
                    proxyIp.setProxyId(String.format("%s:%s", proxyIp.getIpAddress(), proxyIp.getIpPort()));
                    proxyIp.setDataSources("proxylistplus");
                    proxyIps.add(proxyIp);
                }
            } catch (NumberFormatException ignored) {

            }
        }
        return proxyIps;
    }
}

