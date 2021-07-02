package com.chqiuu.proxy.common.util;

import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.apache.hc.client5.http.classic.methods.HttpGet;
import org.apache.hc.client5.http.config.RequestConfig;
import org.apache.hc.client5.http.impl.DefaultSchemePortResolver;
import org.apache.hc.client5.http.impl.classic.CloseableHttpClient;
import org.apache.hc.client5.http.impl.classic.CloseableHttpResponse;
import org.apache.hc.client5.http.impl.classic.HttpClientBuilder;
import org.apache.hc.client5.http.impl.io.PoolingHttpClientConnectionManagerBuilder;
import org.apache.hc.client5.http.impl.routing.DefaultRoutePlanner;
import org.apache.hc.client5.http.io.HttpClientConnectionManager;
import org.apache.hc.client5.http.ssl.NoopHostnameVerifier;
import org.apache.hc.client5.http.ssl.SSLConnectionSocketFactory;
import org.apache.hc.core5.http.HttpHost;
import org.apache.hc.core5.http.ParseException;
import org.apache.hc.core5.http.io.entity.EntityUtils;
import org.apache.hc.core5.http.protocol.HttpContext;
import org.apache.hc.core5.ssl.SSLContexts;
import org.apache.hc.core5.ssl.TrustStrategy;
import org.apache.hc.core5.util.Timeout;

import javax.net.ssl.SSLContext;
import java.io.IOException;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
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
    @SneakyThrows
    public static String get(String urlString, String localIp, HttpHost proxy, Integer timeout, Map<String, String> headers) {
        long startTime = System.currentTimeMillis(), endTime = 0L;
        RequestConfig.Builder builder = RequestConfig.custom();
        if (proxy != null) {
            builder.setProxy(proxy);
        }
        // 设置Cookie策略
        builder.setCookieSpec("standard");
        if (timeout != null) {
            // 设置从connect Manager(连接池)获取Connection 超时时间
            builder.setConnectionRequestTimeout(Timeout.ofMilliseconds(timeout))
                    // 设置连接超时时间，单位毫秒
                    .setConnectTimeout(Timeout.ofMilliseconds(timeout));
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
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        if (timeout != null) {
            // 手动设置Keep-Alive
            httpClientBuilder.setKeepAliveStrategy((response, context) -> Timeout.ofMilliseconds(timeout));
        }
        InetAddress localAddress = getLocalAddress(localIp);
        if (localAddress != null) {
            httpClientBuilder.setRoutePlanner(new DefaultRoutePlanner(DefaultSchemePortResolver.INSTANCE) {
                @SneakyThrows
                @Override
                protected InetAddress determineLocalAddress(final HttpHost firstHop, final HttpContext context) {
                    return localAddress;
                }
            });
        }
        try (CloseableHttpClient httpClient = httpClientBuilder.setConnectionManager(getHttpClientConnectionManager()).build()) {
            CloseableHttpResponse response = httpClient.execute(request);
            return EntityUtils.toString(response.getEntity(), "UTF-8");
        } catch (IOException | ParseException e) {
            endTime = System.currentTimeMillis();
            log.error("{} {} {}", urlString, endTime - startTime, e.getMessage());
        }
        return null;
    }

    public static int getCode(String urlString, String localIp, HttpHost proxy, Integer timeout) {
        return getCode(urlString, localIp, proxy, timeout, null);
    }

    public static int getCode(String urlString, String localIp, HttpHost proxy, Integer timeout, Map<String, String> headers) {
        long startTime = System.currentTimeMillis(), endTime = 0L;
        RequestConfig.Builder builder = RequestConfig.custom();
        if (proxy != null) {
            builder.setProxy(proxy);
        }
        // 设置Cookie策略
        builder.setCookieSpec("standard");
        if (timeout != null) {
            // 设置从connect Manager(连接池)获取Connection 超时时间
            builder.setConnectionRequestTimeout(Timeout.ofMilliseconds(timeout))
                    // 设置连接超时时间，单位毫秒
                    .setConnectTimeout(Timeout.ofMilliseconds(timeout));
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
        HttpClientBuilder httpClientBuilder = HttpClientBuilder.create();
        if (timeout != null) {
            // 手动设置Keep-Alive
            httpClientBuilder.setKeepAliveStrategy((response, context) -> Timeout.ofMilliseconds(timeout));
        }
        InetAddress localAddress = getLocalAddress(localIp);
        if (localAddress != null) {
            httpClientBuilder.setRoutePlanner(new DefaultRoutePlanner(DefaultSchemePortResolver.INSTANCE) {
                @SneakyThrows
                @Override
                protected InetAddress determineLocalAddress(final HttpHost firstHop, final HttpContext context) {
                    return localAddress;
                }
            });
        }
        try (CloseableHttpClient httpClient = httpClientBuilder.setConnectionManager(getHttpClientConnectionManager()).build()) {
            return httpClient.execute(request).getCode();
        } catch (NoSuchAlgorithmException | KeyStoreException | KeyManagementException | IOException e) {
            endTime = System.currentTimeMillis();
            log.error("{} {} {}", urlString, endTime - startTime, e.getMessage());
        }
        return -1;
    }


    private static HttpClientConnectionManager getHttpClientConnectionManager() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        return PoolingHttpClientConnectionManagerBuilder.create()
                .setSSLSocketFactory(getSslConnectionSocketFactory())
                .build();
    }

    /**
     * 支持SSL
     *
     * @return SSLConnectionSocketFactory
     */
    private static SSLConnectionSocketFactory getSslConnectionSocketFactory() throws NoSuchAlgorithmException, KeyStoreException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
        SSLContext sslContext = SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        return new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
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
