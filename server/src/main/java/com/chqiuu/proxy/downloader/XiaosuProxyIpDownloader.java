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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 小舒代理 | http://www.xsdaili.cn/dayProxy/ip/2974.html
 */
@Slf4j
public class XiaosuProxyIpDownloader extends ProxyIpDownloader {
    /**
     * 来源
     */
    private static final String PROXY_DOCMAIN = "xsdaili";
    /**
     * DOMAIN_URL
     */
    private static final String DOMAIN_URL = "http://www.xsdaili.cn";
    /**
     * 爬取页数
     */
    private static final int MAX_PAGES = 200;
    /**
     * 当前已抓取页数
     */
    private int nowPages = 0;

    public XiaosuProxyIpDownloader() {
        super();
    }

    public XiaosuProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        List<ProxyIp> proxyIps = new ArrayList<>();
        String firstBody = NetworkUtil.get(DOMAIN_URL, this.localIp);
        if (StrUtil.isNotEmpty(firstBody)) {
            Document firstBodyDocument = Jsoup.parse(firstBody);
            Elements firstElements = firstBodyDocument.select("body > div.taglineWrap > div > div.col-md-3.admin_arrow_box > div > a");
            for (Element firstElement : firstElements) {
                if (firstElement.attr("href").equals("/")) {
                    continue;
                }
                int subPages = Integer.parseInt(firstElement.select("span").text());
                int pages = (subPages + 15 - 1) / 15;
                String url = firstElement.attr("href");
                url = url.substring(0, url.lastIndexOf("/"));
                for (int page = 1; page <= pages; page++) {
                    if (nowPages < MAX_PAGES) {
                        // 获取文章列表
                        String secondBody = NetworkUtil.get(String.format("%s%s/%s.html", DOMAIN_URL, url, page), this.localIp);
                        proxyIps.addAll(getProxyIpFromPageList(secondBody));
                    } else {
                        break;
                    }
                }
            }
        }
        log.info("downloadProxyIps {} Size {}", PROXY_DOCMAIN, proxyIps.size());
        return proxyIps;
    }

    private List<ProxyIp> getProxyIpFromPageList(String secondBody) {
        List<ProxyIp> proxyIps = new ArrayList<>();
        Document secondDocument = Jsoup.parse(secondBody);
        Elements pageElements = secondDocument.select("body > div.taglineWrap > div > div.col-md-9 > div > div > div > div.panel-body > div > div > div > div.title > a");
        for (Element pageElement : pageElements) {
            if (nowPages < MAX_PAGES) {
                nowPages++;
                proxyIps.addAll(getProxyIpFromPage(NetworkUtil.get(String.format("%s%s", DOMAIN_URL, pageElement.attr("href")), this.localIp)));
            } else {
                break;
            }
        }
        return proxyIps;
    }

    private final static String PROXY_PATTERN_REGEX = "(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)@(\\D+)\\#\\[([\\u4e00-\\u9fa5]+)\\]([\\u4e00-\\u9fa5]+)";

    private List<ProxyIp> getProxyIpFromPage(String body) {
        Document document = Jsoup.parse(body);
        List<ProxyIp> proxyIps = new ArrayList<>();
        if (null == document) {
            return proxyIps;
        }
        Elements ipElements = document.select("body > div.taglineWrap > div > div.col-md-9 > div > div > div > div.panel-body > div > div > div.cont");
        ProxyIp proxyIp;
        for (Element rowElement : ipElements) {
            String[] rows = rowElement.html().split("<br>");
            for (String row : rows) {
                proxyIp = new ProxyIp();
                Matcher matcher = Pattern.compile(PROXY_PATTERN_REGEX).matcher(row.trim());
                while (matcher.find()) {
                    proxyIp.setIpAddress(matcher.group(1));
                    proxyIp.setIpPort(Integer.valueOf(matcher.group(2)));
                    if ("HTTPS".equals(matcher.group(3))) {
                        proxyIp.setHttps(true);
                    }
                    proxyIp.setHttp(true);
                    proxyIp.setAnonymity("高匿名".equals(matcher.group(4)));
                    proxyIp.setLocation(matcher.group(5));
                }
                if (proxyIp.getIpAddress() != null) {
                    proxyIp.setProxyId(String.format("%s:%s", proxyIp.getIpAddress(), proxyIp.getIpPort()));
                    proxyIp.setDataSources(PROXY_DOCMAIN);
                    proxyIps.add(proxyIp);
                }
            }
        }
        return proxyIps;
    }
}
