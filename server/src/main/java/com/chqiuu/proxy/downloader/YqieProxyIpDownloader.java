package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 免费代理IP | http://ip.yqie.com/ipproxy.htm
 */
@Slf4j
public class YqieProxyIpDownloader extends ProxyIpDownloader {

    public YqieProxyIpDownloader() {
        super();
    }

    public YqieProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

