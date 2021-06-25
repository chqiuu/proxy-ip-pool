package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.Data;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 代理下载器接口.
 * crawler 爬行者
 * spider 蜘蛛
 *
 * @author chqiu
 */
@Data
public abstract class ProxyIpDownloader {
    /**
     * 绑定本机IP地址
     */
    protected String localIp;

    protected Map<String, String> headerMap = new HashMap<String, String>() {{
        put("Connection", "keep-alive");
        put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        put("Accept-Encoding", "gzip, deflate, sdch");
        put("Accept-Language", "zh-CN,zh;q=0.9");
        put("Redis-Control", "max-age=0");
        put("Upgrade-Insecure-Requests", "1");
    }};

    /**
     * 批量下载代理IP
     *
     * @return 代理IP列表
     */
    public abstract List<ProxyIp> downloadProxyIps();
}
