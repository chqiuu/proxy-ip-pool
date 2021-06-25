package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *西拉代理 | http://www.xiladaili.com/
 */
@Slf4j
public class XilaProxyIpDownloader extends ProxyIpDownloader {

    public XilaProxyIpDownloader() {
        super();
    }

    public XilaProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

