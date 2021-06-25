package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *ProxyList+ | https://list.proxylistplus.com/
 */
@Slf4j
public class ProxyListPlusProxyIpDownloader extends ProxyIpDownloader {

    public ProxyListPlusProxyIpDownloader() {
        super();
    }

    public ProxyListPlusProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

