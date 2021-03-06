package com.chqiuu.proxy.modules.pool.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.service.IService;
import com.chqiuu.proxy.downloader.model.ProxyIp;
import com.chqiuu.proxy.modules.api.dto.ProxyIpCommonListDTO;
import com.chqiuu.proxy.modules.api.query.ProxyIpCommonPageQuery;
import com.chqiuu.proxy.modules.pool.dto.ProxyIpDetailDTO;
import com.chqiuu.proxy.modules.pool.dto.ProxyIpListDTO;
import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;
import com.chqiuu.proxy.modules.pool.query.ProxyIpListQuery;
import com.chqiuu.proxy.modules.pool.query.ProxyIpPageQuery;

import java.util.List;

/**
 * 代理IP业务逻辑层接口
 *
 * @author chqiuu
 * @date 2021年6月11日
 */
public interface ProxyIpService extends IService<ProxyIpEntity> {

    /**
     * 根据唯一ID获取详细信息
     *
     * @param proxyId 唯一ID
     * @return 详细信息
     */
    ProxyIpDetailDTO getDetailById(String proxyId);

    /**
     * 代理IP列表查询
     *
     * @param query 查询对象
     * @return 代理IP列表
     */
    List<ProxyIpListDTO> getList(ProxyIpListQuery query);

    /**
     * 代理IP分页查询
     *
     * @param query 分页查询对象
     * @return 代理IP列表（带分页）
     */
    IPage<ProxyIpListDTO> getPage(ProxyIpPageQuery query);

    /**
     * 代理IP分页查询（开放接口）
     *
     * @param query 分页查询对象
     * @return 代理IP列表（带分页）
     */
    IPage<ProxyIpCommonListDTO> getCommonPage(ProxyIpCommonPageQuery query);

    /**
     * 批量下载代理IP
     */
    void batchUpdateProxyIp();

    /**
     * 批量保存代理IP到数据库
     *
     * @param proxyIps 代理IP列表
     */
    void saveBatchProxyIp(List<ProxyIp> proxyIps);

    /**
     * 批量监测新增代理IP的有效性
     */
    void batchValidateNewsProxyIp();

    /**
     * 批量监测当前可使用的代理IP有效性
     */
    void batchValidateAvailableProxyIp();

    /**
     * 批量监测当前不可使用的代理IP有效性
     */
    void batchValidateUnavailableProxyIp();

}