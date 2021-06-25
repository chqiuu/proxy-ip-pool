package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 * 飞猪IP代理 | https://www.feizhuip.com/news-getInfo-id-1555.html
 */
@Slf4j
public class FeizhuProxyIpDownloader extends ProxyIpDownloader {

    public FeizhuProxyIpDownloader() {
        super();
    }

    public FeizhuProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}
