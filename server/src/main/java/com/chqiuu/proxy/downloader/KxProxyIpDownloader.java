package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *开心代理 | http://www.kxdaili.com/dailiip.html
 */
@Slf4j
public class KxProxyIpDownloader extends ProxyIpDownloader {

    public KxProxyIpDownloader() {
        super();
    }

    public KxProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

