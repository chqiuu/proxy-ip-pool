package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *Pzzqz | https://pzzqz.com/
 */
@Slf4j
public class PzzqzProxyIpDownloader extends ProxyIpDownloader {

    public PzzqzProxyIpDownloader() {
        super();
    }

    public PzzqzProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

