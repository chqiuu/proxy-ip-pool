package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *小舒代理 | http://www.xsdaili.cn/dayProxy/ip/2974.html
 */
@Slf4j
public class XiaosuProxyIpDownloader extends ProxyIpDownloader {

    public XiaosuProxyIpDownloader() {
        super();
    }

    public XiaosuProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

