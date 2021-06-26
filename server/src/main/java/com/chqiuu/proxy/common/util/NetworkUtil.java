package com.chqiuu.proxy.common.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.http.HttpHost;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.Map;

/**
 * 网络请求工具类
 *
 * @author chqiu
 */
@Slf4j
public class NetworkUtil {

    /**
     * 默认Headers
     */
    protected static Map<String, String> defaultHeaderMap = new HashMap<String, String>() {{
        put("Connection", "keep-alive");
        put("User-Agent", "Mozilla/5.0 (Windows NT 10.0; Win64; x64) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/68.0.3440.106 Safari/537.36");
        put("Accept", "text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*/*;q=0.8");
        put("Accept-Encoding", "gzip, deflate, sdch");
        put("Accept-Language", "zh-CN,zh;q=0.9");
        put("Redis-Control", "max-age=0");
        put("Upgrade-Insecure-Requests", "1");
    }};

    /**
     * 发送get请求
     *
     * @param urlString 网址
     * @return 返回内容，如果只检查状态码，正常只返回 ""，不正常返回 null
     */
    public static String get(String urlString) {
        return get(urlString, null, null, null);
    }

    /**
     * 发送get请求
     *
     * @param urlString 网址
     * @param localIp   绑定本地IP
     * @return 返回内容，如果只检查状态码，正常只返回 ""，不正常返回 null
     */
    public static String get(String urlString, String localIp) {
        return get(urlString, localIp, null, null);
    }

    /**
     * 发送get请求
     *
     * @param urlString 网址
     * @param proxy     代理IP
     * @return 返回内容，如果只检查状态码，正常只返回 ""，不正常返回 null
     */
    public static String get(String urlString, HttpHost proxy) {
        return get(urlString, null, proxy, null);
    }

    /**
     * 发送get请求
     *
     * @param urlString 网址
     * @param localIp   绑定本地IP
     * @param proxy     代理IP
     * @return 返回内容，如果只检查状态码，正常只返回 ""，不正常返回 null
     */
    public static String get(String urlString, String localIp, HttpHost proxy) {
        return get(urlString, localIp, proxy, null, null);
    }

    /**
     * 发送get请求
     *
     * @param urlString 网址
     * @param localIp   绑定本地IP
     * @param proxy     代理IP
     * @param timeout   超时时间，单位-毫秒
     * @return 返回内容，如果只检查状态码，正常只返回 ""，不正常返回 null
     */
    public static String get(String urlString, String localIp, HttpHost proxy, Integer timeout) {
        return get(urlString, localIp, proxy, timeout, null);
    }

    /**
     * 发送get请求
     *
     * @param urlString 网址
     * @param localIp   绑定本地IP
     * @param proxy     代理IP
     * @param timeout   超时时间，单位-毫秒
     * @param headers   Headers数组
     * @return 返回内容，如果只检查状态码，正常只返回 ""，不正常返回 null
     */
    public static String get(String urlString, String localIp, HttpHost proxy, Integer timeout, Map<String, String> headers) {
        RequestConfig.Builder builder = RequestConfig.custom();
        InetAddress localAddress = getLocalAddress(localIp);
        if (localAddress != null) {
            builder.setLocalAddress(localAddress);
        }
        if (proxy != null) {
            builder.setProxy(proxy);
        }
        // 设置Cookie策略
        builder.setCookieSpec(CookieSpecs.STANDARD);
        if (timeout != null) {
            // 配置请求的超时设置
            builder.setConnectionRequestTimeout(timeout)
                    .setConnectTimeout(timeout)
                    .setSocketTimeout(timeout);
        }
        RequestConfig config = builder.build();
        HttpGet request = new HttpGet(urlString);
        if (null == headers) {
            headers = defaultHeaderMap;
        }
        for (String key : headers.keySet()) {
            //设置请求头，将爬虫伪装成浏览器
            request.addHeader(key, headers.get(key));
        }
        request.setConfig(config);
        try (CloseableHttpClient httpClient = HttpClients.createDefault()) {
            CloseableHttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException e) {
            log.error("{} {}", urlString, e.getMessage());
        }
        return null;
    }

    private static InetAddress getLocalAddress(String localIp) {
        if (null == localIp) {
            return null;
        }
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
