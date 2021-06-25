package com.chqiuu.proxy.modules.pool.manager.impl;

import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;
import com.chqiuu.proxy.modules.pool.manager.ProxyIpManager;
import com.chqiuu.proxy.modules.pool.mapper.ProxyIpMapper;
import lombok.AllArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class ProxyIpManagerImpl implements ProxyIpManager {

    private final ProxyIpMapper proxyIpMapper;

    @Async
    @Override
    public void validateProxyIp(ProxyIpEntity entity, List<String> testUrls) {

    }
}
