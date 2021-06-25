package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *高可用全球免费代理库 | https://ip.jiangxianli.com/
 */
@Slf4j
public class JiangxianliProxyIpDownloader extends ProxyIpDownloader {

    public JiangxianliProxyIpDownloader() {
        super();
    }

    public JiangxianliProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

