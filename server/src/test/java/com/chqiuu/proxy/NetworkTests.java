package com.chqiuu.proxy;

import com.chqiuu.proxy.common.util.NetworkUtil;
import org.apache.hc.core5.http.HttpHost;
import org.junit.jupiter.api.Test;

import java.net.UnknownHostException;

public class NetworkTests {
    @Test
    void get() throws UnknownHostException {
        String url = "https://blog.csdn.net/QIU176161650/article/details/117445077";
        //  url = "http://www.it-teaching.com/material.html"; http://104.129.198.95:8800
        HttpHost proxy = new HttpHost("104.129.198.95", 8080);
        String html = NetworkUtil.get(url, "192.168.2.103", proxy, 2000);
        String html1 = NetworkUtil.get(url, "192.168.2.103");
        System.out.println(html1);
    }
}
