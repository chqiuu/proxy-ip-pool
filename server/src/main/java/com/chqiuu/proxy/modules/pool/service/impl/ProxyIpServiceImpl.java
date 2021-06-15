package com.chqiuu.proxy.modules.pool.service.impl;
import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;
import com.chqiuu.proxy.modules.pool.mapper.ProxyIpMapper;
import com.chqiuu.proxy.modules.pool.query.ProxyIpListQuery;
import com.chqiuu.proxy.modules.pool.query.ProxyIpPageQuery;
import com.chqiuu.proxy.modules.pool.service.ProxyIpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

import lombok.AllArgsConstructor;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.chqiuu.proxy.modules.pool.dto.ProxyIpDetailDTO;
import com.chqiuu.proxy.modules.pool.dto.ProxyIpListDTO;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

/**
 * 代理IP业务逻辑层实现
 *
 * @author chqiuu
 * @date 2021年6月11日
 */
@Slf4j
@Service
@AllArgsConstructor
public class ProxyIpServiceImpl extends ServiceImpl<ProxyIpMapper, ProxyIpEntity> implements ProxyIpService {

    @Override
    public ProxyIpDetailDTO getDetailById(String proxyId) {
        return this.baseMapper.getDetailById(proxyId);
    }
    @Override
    public List<ProxyIpListDTO> getList(ProxyIpListQuery query) {
        return this.baseMapper.getList(query);
    }
    @Override
    public IPage<ProxyIpListDTO> getPage(ProxyIpPageQuery query) {
        Page<ProxyIpListDTO> pageInfo = new Page<>(query.getCurrent(), query.getSize());
        return this.baseMapper.getPage(pageInfo, query);
    }
}