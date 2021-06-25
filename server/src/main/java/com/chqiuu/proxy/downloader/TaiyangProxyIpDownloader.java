package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;

import java.util.List;

/**
 *太阳HTTP | http://http.taiyangruanjian.com/free/
 */
@Slf4j
public class TaiyangProxyIpDownloader extends ProxyIpDownloader {

    public TaiyangProxyIpDownloader() {
        super();
    }

    public TaiyangProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        return null;
    }
}

