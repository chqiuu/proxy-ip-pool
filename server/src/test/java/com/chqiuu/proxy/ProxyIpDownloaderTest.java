package com.chqiuu.proxy;

import com.chqiuu.proxy.downloader.*;
import com.chqiuu.proxy.downloader.model.ProxyIp;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ProxyIpDownloaderTest {

    // private final static String LOCAL_IP = "192.168.2.103";
    private final static String LOCAL_IP = null;

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
    void ip89() {
        // 89免费代理
        List<ProxyIp> proxyIps = new Ip89ProxyIpDownloader(LOCAL_IP).downloadProxyIps();
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

    @Test
    void superFast() {
        // 极速专享代理
        List<ProxyIp> proxyIps = new SuperFastProxyIpDownloader(LOCAL_IP).downloadProxyIps();
        proxyIps.forEach(System.out::println);
    }

    @Test
    void taiyangruanjian() {
        // 太阳HTTP
        List<ProxyIp> proxyIps = new TaiyangProxyIpDownloader(LOCAL_IP).downloadProxyIps();
        proxyIps.forEach(System.out::println);
    }

    @Test
    void xsdaili() {
        // 小舒代理
        List<ProxyIp> proxyIps = new XiaosuProxyIpDownloader(LOCAL_IP).downloadProxyIps();
        proxyIps.forEach(System.out::println);
    }

    @Test
    void zz() {
        String str = "121.230.225.157:9999@HTTP#[高匿名]江苏泰州 电信";
        String regex = "(\\d+\\.\\d+\\.\\d+\\.\\d+)\\:(\\d+)@(\\D+)\\#\\[([\\u4e00-\\u9fa5]+)\\]([\\u4e00-\\u9fa5]+)";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(str);
        System.out.println(matcher.groupCount());
        while (matcher.find()) {
            System.out.println(matcher.group(0));
            System.out.println(matcher.group(1));
            System.out.println(matcher.group(2));
            System.out.println(matcher.group(3));
            System.out.println(matcher.group(4));
            System.out.println(matcher.group(5));
        }
    }

    @Test
    void regex() {
        // 这个正则表达式有两个组，
// group(0) 是 \\$\\{([^{}]+?)\\}
// group(1) 是 ([^{}]+?)
        String regex = "\\$\\{([^{}]+?)\\}";
        Pattern pattern = Pattern.compile(regex);
        String input = "${name}-babalala-${age}-${address}";

        Matcher matcher = pattern.matcher(input);
        System.out.println(matcher.groupCount());
// find() 像迭代器那样向前遍历输入字符串
        while (matcher.find()) {
            System.out.println(matcher.group(0) + ", pos: "
                    + matcher.start() + "-" + (matcher.end() - 1));
            System.out.println(matcher.group(1) + ", pos: " +
                    matcher.start(1) + "-" + (matcher.end(1) - 1));
        }
    }
}
