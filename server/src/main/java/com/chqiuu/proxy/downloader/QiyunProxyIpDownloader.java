package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *齐云代理 | https://www.7yip.cn/free/
 */
@Slf4j
public class QiyunProxyIpDownloader extends ProxyIpDownloader {

    public QiyunProxyIpDownloader() {
        super();
    }

    public QiyunProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

