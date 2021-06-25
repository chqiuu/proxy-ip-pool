package com.chqiuu.proxy.common.enums;

import lombok.Getter;

/**
 * 代理IP枚举
 *
 * @author chqiu
 */
@Getter
public enum ProxyDomainEnum {
    /**
     * 代理IP枚举
     */
    HUAI_DAI_LI("快代理", "https://www.kuaidaili.com/free/"),
    PROXY_LIST_PLUS("ProxyList+", "https://list.proxylistplus.com/"),
    YQIE("免费代理IP", "http://ip.yqie.com/ipproxy.htm"),
    PROXY_66IP("66免费代理", "http://www.66ip.cn/"),
    PROXY_89IP("89免费代理", "http://www.89ip.cn/"),
    IP3366("云代理", "http://www.ip3366.net/"),
    SUPER_FAST_IP("极速专享代理", "http://www.superfastip.com/"),
    XILA_DAILI("西拉代理", "http://www.xiladaili.com/"),
    IHUAN("小幻HTTP代理", "https://ip.ihuan.me/"),
    XS_DAILI("小舒代理", "http://www.xsdaili.cn/dayProxy/ip/2974.html"),
    FEIZHUIP("飞猪IP代理", "https://www.feizhuip.com/news-getInfo-id-1555.html"),
    QIYUN("齐云代理", "https://www.7yip.cn/free/"),
    KX_DAILI("开心代理", "http://www.kxdaili.com/dailiip.html"),
    NIMA_DAILI("泥马代理", "http://www.nimadaili.com/"),
    TAIYANG_RUANJIAN("太阳HTTP", "http://http.taiyangruanjian.com/free/"),
    JIANG_XIANLI("高可用全球免费代理库", "https://ip.jiangxianli.com/"),
    MIMVP("米扑代理", "https://proxy.mimvp.com/freesecret"),
    PZZQZ("Pzzqz", "https://pzzqz.com/"),
    SHENJI_DAILI("神鸡代理", "http://www.shenjidaili.com/product/open/");

    /**
     * 代理名称
     */
    private String proxyName;
    /**
     * 代理地址
     */
    private String url;

    ProxyDomainEnum(String proxyName, String url) {
        this.proxyName = proxyName;
        this.url = url;
    }
}
