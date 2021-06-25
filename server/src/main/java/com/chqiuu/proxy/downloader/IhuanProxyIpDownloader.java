package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 小幻HTTP代理 | https://ip.ihuan.me/
 */
@Slf4j
public class IhuanProxyIpDownloader extends ProxyIpDownloader {

    public IhuanProxyIpDownloader() {
        super();
    }

    public IhuanProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

