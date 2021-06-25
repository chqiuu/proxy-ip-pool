package com.chqiuu.proxy.downloader;

import com.chqiuu.proxy.downloader.model.ProxyIp;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.List;

/**
 * |神鸡代理 | http://www.shenjidaili.com/product/open/
 *
 * @author chqiu
 */
@Slf4j
public class ShenjidailiProxyIpDownloader extends ProxyIpDownloader {
    private static final String URL = "http://www.shenjidaili.com/product/open/";

    public ShenjidailiProxyIpDownloader() {
        super();
    }

    public ShenjidailiProxyIpDownloader(String localIp) {
        this.localIp = localIp;
    }

    @Override
    public List<ProxyIp> downloadProxyIps() {
        List<ProxyIp> proxyIps = new ArrayList<>();
        String body = getBody(this.localIp);
        if (null == body) {
            return null;
        }
        Document document = Jsoup.parse(body);
        Elements ipElements = document.select("#pills-tabContent table tbody tr");
        ProxyIp proxyIp = new ProxyIp();
        for (Element rowElement : ipElements) {
            if ("免费ip".equals(rowElement.selectFirst("td").text().trim())) {
                log.info("表头跳过{}", rowElement);
                continue;
            }
            proxyIp = new ProxyIp();
            Elements fieldElements = rowElement.select("td");
            for (int i = 0; i < fieldElements.size(); i++) {
                if (i == 0) {
                    proxyIp.setIpAddress(fieldElements.get(i).text().trim().split(":")[0]);
                } else if (i == 1) {
                    proxyIp.setIpPort(Integer.valueOf(fieldElements.get(i).text().trim()));
                } else if (i == 2) {
                    // 匿名度
                    if ("高匿".equals(fieldElements.get(i).text().trim())) {
                        proxyIp.setAnonymity(true);
                    } else {
                        proxyIp.setAnonymity(false);
                    }
                } else if (i == 3) {
                    String type = fieldElements.get(i).text().trim().toLowerCase();
                    if (type.contains("https")) {
                        proxyIp.setHttps(true);
                        type = type.replaceAll("https", "");
                    }
                    if (type.contains("http")) {
                        proxyIp.setHttp(true);
                    }
                } else if (i == 4) {
                    proxyIp.setCountry(fieldElements.get(i).text().trim().split(" ")[0]);
                    proxyIp.setLocation(fieldElements.get(i).text().trim());

                }
                proxyIp.setAvailable(true);
            }
            proxyIp.setProxyId(String.format("%s:%s", proxyIp.getIpAddress(), proxyIp.getIpPort()));
            log.info("代理IP {}", proxyIp);
            proxyIps.add(proxyIp);
        }
        return proxyIps;
    }

    private String getBody(String localIp) {
        RequestConfig.Builder builder = RequestConfig.custom();
        InetAddress localAddress = getLocalAddress(localIp);
        if (localAddress != null) {
            builder.setLocalAddress(localAddress);
        }
        RequestConfig config = builder.build();
        HttpGet request = new HttpGet(URL);
        //设置请求头，将爬虫伪装成浏览器
        request.setHeader("User-Agent", "Mozilla/5.0 (Windows NT 6.1) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/74.0.3729.169 Safari/537.36");
        request.setConfig(config);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

    private InetAddress getLocalAddress(String localIp) {
        String[] ipStr = localIp.split("\\.");
        byte[] localAddressByte = new byte[4];
        for (int i = 0; i < 4; i++) {
            localAddressByte[i] = (byte) (Integer.parseInt(ipStr[i]) & 0xff);
        }
        try {
            return InetAddress.getByAddress(localAddressByte);
        } catch (UnknownHostException e) {
            return null;
        }
    }
}

