package com.chqiuu.proxy.downloader;

import cn.hutool.core.util.StrUtil;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.chqiuu.proxy.common.util.NetworkUtil;
import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 极速专享代理 | http://www.superfastip.com/
 */
@Slf4j
public class SuperFastProxyIpDownloader extends ProxyIpDownloader {

    /**
     * 默认Headers
     */
    protected static Map<String, String> headerMap = new HashMap<String, String>() {{
        put("Connection", "keep-alive");
        put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        put("Accept-Encoding", "gzip, deflate, sdch");
        put("Accept-Language", "zh-CN,zh;q=0.9");
        put("Redis-Control", "max-age=0");
        put("Content-type", "text/json");
        put("Upgrade-Insecure-Requests", "1");
    }};
    /**
     * 来源
     */
    private static final String PROXY_DOCMAIN = "superfastip";
    /**
     * 代理URL模板
     */
    private static final String URL_TEMPLATE = "https://api.superfastip.com/ip/freeip?page=%s";
    /**
     * 爬取页数
     */
    private static final int MAX_PAGES = 10;

    public SuperFastProxyIpDownloader() {
        super();
    }

    public SuperFastProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        List<ProxyIp> proxyIps = new ArrayList<>();
        for (int i = 1; i <= MAX_PAGES; i++) {
            proxyIps.addAll(getProxyIpFromPage(NetworkUtil.get(String.format(URL_TEMPLATE, i), this.localIp, null, null, headerMap)));
        }
        log.info("downloadProxyIps {} Size {}", PROXY_DOCMAIN, proxyIps.size());
        return proxyIps;
    }

    private List<ProxyIp> getProxyIpFromPage(String body) {
        List<ProxyIp> proxyIps = new ArrayList<>();
        if (null == body) {
            return proxyIps;
        }
        JSONObject jsonObject = JSONObject.parseObject(body);
        JSONArray rows = jsonObject.getJSONArray("freeips");
        ProxyIp proxyIp = new ProxyIp();
        for (int i = 0; i < rows.size(); i++) {
            proxyIp = new ProxyIp();
            JSONObject row = rows.getJSONObject(i);
            proxyIp.setIpAddress(row.getString("ip"));
            proxyIp.setIpPort(row.getInteger("port"));
            proxyIp.setAnonymity(row.getString("level").contains("匿名"));
            String type = row.getString("type").toLowerCase();
            if (type.contains("https")) {
                proxyIp.setHttps(true);
                type = type.replaceAll("https", "");
            }
            proxyIp.setHttp(true);
            proxyIp.setCountry(row.getString("country"));
            proxyIp.setLocation(row.getString("country"));
            if (StrUtil.isNotEmpty(proxyIp.getIpAddress()) && proxyIp.getIpPort() != null) {
                proxyIp.setAvailable(true);
                proxyIp.setProxyId(String.format("%s:%s", proxyIp.getIpAddress(), proxyIp.getIpPort()));
                proxyIp.setDataSources(PROXY_DOCMAIN);
                proxyIps.add(proxyIp);
            }
        }
        return proxyIps;
    }
}