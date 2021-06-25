package com.chqiuu.proxy.common.http;

import cn.hutool.http.HttpRequest;

import java.nio.charset.Charset;

public class HttpUtil {

    /**
     * 发送get请求
     *
     * @param urlString 网址
     * @return 返回内容，如果只检查状态码，正常只返回 ""，不正常返回 null
     */
    public static String get(String urlString) {
        return get(urlString, -1);
    }

    /**
     * 发送get请求
     *
     * @param urlString 网址
     * @param timeout   超时时长，-1表示默认超时，单位毫秒
     * @return 返回内容，如果只检查状态码，正常只返回 ""，不正常返回 null
     * @since 3.2.0
     */
    public static String get(String urlString, int timeout) {
        return HttpRequest.get(urlString).timeout(timeout).execute().body();
    }

    /**
     * 发送get请求
     *
     * @param urlString     网址
     * @param customCharset 自定义请求字符集，如果字符集获取不到，使用此字符集
     * @return 返回内容，如果只检查状态码，正常只返回 ""，不正常返回 null
     */
    public static String get(String urlString, Charset customCharset) {
        return HttpRequest.get(urlString).charset(customCharset).execute().body();
    }
}
