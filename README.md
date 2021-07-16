# proxy-ip-pool项目
本项目是基于Springboot+Mysql数据库开发的代理IP数据库池。主要功能：自动采集免费代理IP、定时池中代理IP有效性、提供代理IP对外接口等功能。开箱即用

### IP来源
| 代理名称 | 推荐指数 | 地址 | 备注 |
|:--------:| -------------:| -------------:| -------------:|
|快代理 | ★★★ | https://www.kuaidaili.com/free/ | |
|神鸡代理 | ☆ | http://www.shenjidaili.com/product/open/ | |
|ProxyList+ | ☆ | https://list.proxylistplus.com/ | |
|免费代理IP | ☆ | http://ip.yqie.com/ipproxy.htm | 更新不及时，老数据 |
|66免费代理 | ★★ | http://www.66ip.cn/ | |
|89免费代理 | ★★ | http://www.89ip.cn/ | |
|云代理 | ★★ | http://www.ip3366.net/ | |
|极速专享代理 | | http://www.superfastip.com/ | |
|西拉代理 | ★ | http://www.xiladaili.com/ | |
|小幻HTTP代理 | ★★★★ | https://ip.ihuan.me/ | |
|小舒代理 | ★★☆ | http://www.xsdaili.cn/dayProxy/ip/2974.html | 数据量大 |
|飞猪IP代理 | ★★☆ | https://www.feizhuip.com/news-getInfo-id-1555.html | |
|齐云代理 | ★ | https://www.7yip.cn/free/ | |
|开心代理 | ★☆ | http://www.kxdaili.com/dailiip.html | |
|泥马代理 | ★★☆ | http://www.nimadaili.com/ | |
|太阳HTTP | ★ | http://http.taiyangruanjian.com/free/ | |
|高可用全球免费代理库 | ☆ | https://ip.jiangxianli.com/ | |
|米扑代理 | ☆ | https://proxy.mimvp.com/freesecret | |
|Pzzqz | ☆ | https://pzzqz.com/ | |

# 数据库表结构

```mysql
CREATE TABLE `proxy_ip` (
  `proxy_id` varchar(30) NOT NULL COMMENT '唯一ID',
  `ip_address` varchar(255) NOT NULL COMMENT 'IP地址',
  `ip_port` int(11) NOT NULL COMMENT 'IP端口',
  `data_sources` varchar(255) DEFAULT NULL COMMENT '数据来源',
  `country` varchar(255) DEFAULT NULL COMMENT '国家',
  `location` varchar(255) DEFAULT NULL COMMENT '位置',
  `https` tinyint(4) DEFAULT '0' COMMENT '支持https',
  `http` tinyint(4) DEFAULT '0' COMMENT '支持http',
  `anonymity` tinyint(4) DEFAULT '0' COMMENT '匿名性',
  `available` tinyint(4) DEFAULT '0' COMMENT '可用性',
  `last_validate_time` datetime DEFAULT NULL COMMENT '最近校验时间',
  `validate_count` int(11) DEFAULT '0' COMMENT '校验次数',
  `available_count` int(11) DEFAULT '0' COMMENT '校验可用次数',
  `unavailable_count` int(11) DEFAULT '0' COMMENT '校验不可用次数',
  `failure_time` datetime DEFAULT NULL COMMENT '失效时间',
  `use_times` bigint(20) DEFAULT '0' COMMENT '代理请求需要总时长',
  `avg_use_times` bigint(20) DEFAULT '0' COMMENT '代理请求需要平均时长',
  `available_rate` double(6,4) DEFAULT '0.0000' COMMENT '可用率',
  `use_size` int(11) unsigned DEFAULT '0' COMMENT '使用次数',
  `fail_size` int(11) unsigned DEFAULT '0' COMMENT '失败次数',
  `create_time` datetime DEFAULT CURRENT_TIMESTAMP COMMENT '创建时间',
  `update_time` datetime DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP COMMENT '修改时间',
  PRIMARY KEY (`proxy_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COMMENT='代理IP表';
```