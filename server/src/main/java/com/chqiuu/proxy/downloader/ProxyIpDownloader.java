package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.Data;

import java.util.List;

/**
 * 代理下载器接口.
 * crawler 爬行者
 * spider 蜘蛛
 *
 * @author chqiu
 */
@Data
public abstract class ProxyIpDownloader {
    /**
     * 绑定本机IP地址
     */
    protected String localIp;

    /**
     * 批量下载代理IP
     *
     * @return 代理IP列表
     */
    public abstract List<ProxyIp> downloadProxyIps();
}
