package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *米扑代理 | https://proxy.mimvp.com/freesecret
 */
@Slf4j
public class MimvpProxyIpDownloader extends ProxyIpDownloader {

    public MimvpProxyIpDownloader() {
        super();
    }

    public MimvpProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

