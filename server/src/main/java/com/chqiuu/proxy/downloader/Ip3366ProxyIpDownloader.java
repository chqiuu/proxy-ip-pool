package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *云代理 | http://www.ip3366.net/
 */
@Slf4j
public class Ip3366ProxyIpDownloader extends ProxyIpDownloader {

    public Ip3366ProxyIpDownloader() {
        super();
    }

    public Ip3366ProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

