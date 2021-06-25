package com.chqiuu.proxy;

import cn.hutool.http.HttpUtil;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

public class CsdnTest {

    @Test
    void csdnList() {
        List<String> list = new ArrayList<>();
        String body = HttpUtil.get("https://blog.csdn.net/QIU176161650/article/list");
        Document document = Jsoup.parse(body);
        Elements articleElements = document.select("div.article-list > div > h4 > a");
        for (Element articleElement : articleElements) {
            list.add(articleElement.attr("href"));
        }
        list.forEach(System.out::println);
    }
}
