package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *极速专享代理 | http://www.superfastip.com/
 */
@Slf4j
public class SuperFastProxyIpDownloader extends ProxyIpDownloader {

    public SuperFastProxyIpDownloader() {
        super();
    }

    public SuperFastProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

