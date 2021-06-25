package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *89免费代理 | http://www.89ip.cn/
 */
@Slf4j
public class Ip89ProxyIpDownloader extends ProxyIpDownloader {

    public Ip89ProxyIpDownloader() {
        super();
    }

    public Ip89ProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

