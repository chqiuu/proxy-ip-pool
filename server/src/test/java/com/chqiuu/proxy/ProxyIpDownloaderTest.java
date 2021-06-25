package com.chqiuu.proxy;

import com.chqiuu.proxy.downloader.KuaidailiProxyIpDownloader;
import com.chqiuu.proxy.downloader.ShenjidailiProxyIpDownloader;
import com.chqiuu.proxy.downloader.model.ProxyIp;
import org.junit.jupiter.api.Test;

import java.util.List;

public class ProxyIpDownloaderTest {

    private final static String LOCAL_IP = "192.168.2.103";

    @Test
    void kuaidaili() {
        // 快代理
        List<ProxyIp> proxyIps = new KuaidailiProxyIpDownloader(LOCAL_IP).downloadProxyIps();
        proxyIps.forEach(System.out::println);

/*

|ProxyList+ | https://list.proxylistplus.com/|   ProxyListPlusProxyIpDownloader
|免费代理IP | http://ip.yqie.com/ipproxy.htm|     YqieProxyIpDownloader
|66免费代理 | http://www.66ip.cn/|    Ip66ProxyIpDownloader
|89免费代理 | http://www.89ip.cn/|    Ip89ProxyIpDownloader
|云代理 | http://www.ip3366.net/|    Ip3366ProxyIpDownloader
|极速专享代理 | http://www.superfastip.com/|    SuperFastProxyIpDownloader
|西拉代理 | http://www.xiladaili.com/|    XilaProxyIpDownloader
|小幻HTTP代理 | https://ip.ihuan.me/|    IhuanProxyIpDownloader
|小舒代理 | http://www.xsdaili.cn/dayProxy/ip/2974.html|    XiaosuProxyIpDownloader
|飞猪IP代理 | https://www.feizhuip.com/news-getInfo-id-1555.html|    FeizhuProxyIpDownloader
|齐云代理 | https://www.7yip.cn/free/|    QiyunProxyIpDownloader
|开心代理 | http://www.kxdaili.com/dailiip.html|    KxProxyIpDownloader
|泥马代理 | http://www.nimadaili.com/|    NimaProxyIpDownloader
|太阳HTTP | http://http.taiyangruanjian.com/free/|    TaiyangProxyIpDownloader
|高可用全球免费代理库 | https://ip.jiangxianli.com/|    JiangxianliProxyIpDownloader
|米扑代理 | https://proxy.mimvp.com/freesecret|    MimvpProxyIpDownloader
|Pzzqz | https://pzzqz.com/|    PzzqzProxyIpDownloader
 */

    }



    @Test
    void shenjidaili() {
        // 神鸡代理
        List<ProxyIp> proxyIps = new ShenjidailiProxyIpDownloader(LOCAL_IP).downloadProxyIps();
        proxyIps.forEach(System.out::println);
    }
}
