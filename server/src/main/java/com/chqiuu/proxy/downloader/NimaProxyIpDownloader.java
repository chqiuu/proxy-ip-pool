package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *泥马代理 | http://www.nimadaili.com/
 */
@Slf4j
public class NimaProxyIpDownloader extends ProxyIpDownloader {

    public NimaProxyIpDownloader() {
        super();
    }

    public NimaProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

