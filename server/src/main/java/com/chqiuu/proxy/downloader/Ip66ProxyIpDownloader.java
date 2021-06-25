package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *66免费代理 | http://www.66ip.cn/
 */
@Slf4j
public class Ip66ProxyIpDownloader extends ProxyIpDownloader {

    public Ip66ProxyIpDownloader() {
        super();
    }

    public Ip66ProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

