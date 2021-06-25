package com.chqiuu.proxy;

import com.chqiuu.proxy.downloader.*;
import com.chqiuu.proxy.downloader.model.ProxyIp;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProxyIpDownloaderTest {

    private final static String LOCAL_IP = "192.168.2.103";

    @Test
    void feizhuip() {
        // 飞猪IP代理
        List<ProxyIp> proxyIps = new FeizhuProxyIpDownloader(LOCAL_IP).downloadProxyIps();
        proxyIps.forEach(System.out::println);
    }

    @Test
    void ihuan() {
        // 小幻HTTP代理
        List<ProxyIp> proxyIps = new IhuanProxyIpDownloader(LOCAL_IP).downloadProxyIps();
        proxyIps.forEach(System.out::println);
    }

    @Test
    void ip66() {
        // 66免费代理
        List<ProxyIp> proxyIps = new Ip66ProxyIpDownloader(LOCAL_IP).downloadProxyIps();
        proxyIps.forEach(System.out::println);
    }

    @Test
    void kuaidaili() {
        // 快代理
        List<ProxyIp> proxyIps = new KuaidailiProxyIpDownloader(LOCAL_IP).downloadProxyIps();
        proxyIps.forEach(System.out::println);
    }

    @Test
    void proxyListPlus() {
        // ProxyList+
        List<ProxyIp> proxyIps = new ProxyListPlusProxyIpDownloader(LOCAL_IP).downloadProxyIps();
        proxyIps.forEach(System.out::println);
    }

    @Test
    void shenjidaili() {
        // 神鸡代理
        List<ProxyIp> proxyIps = new ShenjidailiProxyIpDownloader(LOCAL_IP).downloadProxyIps();
        proxyIps.forEach(System.out::println);
    }
}
