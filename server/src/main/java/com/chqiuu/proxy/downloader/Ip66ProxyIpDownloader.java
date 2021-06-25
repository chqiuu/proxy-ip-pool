package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 66免费代理 | http://www.66ip.cn/
 */
@Slf4j
public class Ip66ProxyIpDownloader extends ProxyIpDownloader {

    protected static Map<String, String> headerMap = new HashMap<String, String>() {{
        put("Connection", "keep-alive");
        put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8; charset=gb2312");
        put("Accept-Encoding", "gzip, deflate, sdch");
        put("Content-Type", "text/html; charset=gb2312");
        put("Accept-Language", "zh-CN,zh;q=0.9");
        put("Redis-Control", "max-age=0");
        put("Upgrade-Insecure-Requests", "1");
    }};
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
                proxyIp.setDataSources("kuaidaili");
                proxyIps.add(proxyIp);
            }
        }
        return proxyIps;
    }
}

