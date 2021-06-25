package com.chqiuu.proxy.modules.pool.mapper;

import com.chqiuu.proxy.modules.pool.query.ProxyIpListQuery;
import com.chqiuu.proxy.modules.pool.query.ProxyIpPageQuery;
import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.chqiuu.proxy.modules.pool.dto.ProxyIpDetailDTO;
import com.chqiuu.proxy.modules.pool.dto.ProxyIpListDTO;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import java.util.List;

/**
 * 代理IP数据持久层
 *
 * @author chqiuu
 * @date 2021年6月11日
 */
@Repository
public interface ProxyIpMapper extends BaseMapper<ProxyIpEntity> {
    /**
     * 插入数据，如果中已经存在相同的记录，则忽略当前新数据
     *
     * @param entity 实体类对象
     * @return 影响条数
     */
    @Insert("<script>INSERT IGNORE INTO `proxy_ip` <trim prefix='(' suffix=')' suffixOverrides=','><if test='proxyId != null'>`proxy_id`, </if><if test='ipAddress != null'>`ip_address`, </if><if test='ipPort != null'>`ip_port`, </if><if test='dataSources != null'>`data_sources`, </if><if test='country != null'>`country`, </if><if test='location != null'>`location`, </if><if test='https != null'>`https`, </if><if test='http != null'>`http`, </if><if test='anonymity != null'>`anonymity`, </if><if test='available != null'>`available`, </if><if test='lastValidateTime != null'>`last_validate_time`, </if><if test='validateCount != null'>`validate_count`, </if><if test='availableCount != null'>`available_count`, </if><if test='unAvailableCount != null'>`un_available_count`, </if><if test='failureTime != null'>`failure_time`, </if><if test='useTimes != null'>`use_times`, </if><if test='availableRate != null'>`available_rate`, </if><if test='useSize != null'>`use_size`, </if><if test='failSize != null'>`fail_size`, </if><if test='createTime != null'>`create_time`, </if><if test='updateTime != null'>`update_time`, </if></trim><trim prefix='values (' suffix=')' suffixOverrides=','><if test='proxyId != null'>#{proxyId}, </if><if test='ipAddress != null'>#{ipAddress}, </if><if test='ipPort != null'>#{ipPort}, </if><if test='country != null'>#{country}, </if><if test='location != null'>#{location}, </if><if test='https != null'>#{https}, </if><if test='http != null'>#{http}, </if><if test='anonymity != null'>#{anonymity}, </if><if test='available != null'>#{available}, </if><if test='lastValidateTime != null'>#{lastValidateTime}, </if><if test='validateCount != null'>#{validateCount}, </if><if test='availableCount != null'>#{availableCount}, </if><if test='unAvailableCount != null'>#{unAvailableCount}, </if><if test='failureTime != null'>#{failureTime}, </if><if test='useTimes != null'>#{useTimes}, </if><if test='availableRate != null'>#{availableRate}, </if><if test='useSize != null'>#{useSize}, </if><if test='failSize != null'>#{failSize}, </if><if test='createTime != null'>#{createTime}, </if><if test='updateTime != null'>#{updateTime}, </if></trim></script>")
    int insertIgnore(ProxyIpEntity entity);

    /**
     * 替换数据，如果中已经存在相同的记录，则覆盖旧数据
     *
     * @param entity 实体类对象
     * @return 影响条数
     */
    @Insert("<script>REPLACE INTO `proxy_ip` <trim prefix='(' suffix=')' suffixOverrides=','><if test='proxyId != null'>`proxy_id`, </if><if test='ipAddress != null'>`ip_address`, </if><if test='ipPort != null'>`ip_port`, </if><if test='dataSources != null'>`data_sources`, </if><if test='country != null'>`country`, </if><if test='location != null'>`location`, </if><if test='https != null'>`https`, </if><if test='http != null'>`http`, </if><if test='anonymity != null'>`anonymity`, </if><if test='available != null'>`available`, </if><if test='lastValidateTime != null'>`last_validate_time`, </if><if test='validateCount != null'>`validate_count`, </if><if test='availableCount != null'>`available_count`, </if><if test='unAvailableCount != null'>`un_available_count`, </if><if test='failureTime != null'>`failure_time`, </if><if test='useTimes != null'>`use_times`, </if><if test='availableRate != null'>`available_rate`, </if><if test='useSize != null'>`use_size`, </if><if test='failSize != null'>`fail_size`, </if><if test='createTime != null'>`create_time`, </if><if test='updateTime != null'>`update_time`, </if></trim><trim prefix='values (' suffix=')' suffixOverrides=','><if test='proxyId != null'>#{proxyId}, </if><if test='ipAddress != null'>#{ipAddress}, </if><if test='ipPort != null'>#{ipPort}, </if><if test='country != null'>#{country}, </if><if test='location != null'>#{location}, </if><if test='https != null'>#{https}, </if><if test='http != null'>#{http}, </if><if test='anonymity != null'>#{anonymity}, </if><if test='available != null'>#{available}, </if><if test='lastValidateTime != null'>#{lastValidateTime}, </if><if test='validateCount != null'>#{validateCount}, </if><if test='availableCount != null'>#{availableCount}, </if><if test='unAvailableCount != null'>#{unAvailableCount}, </if><if test='failureTime != null'>#{failureTime}, </if><if test='useTimes != null'>#{useTimes}, </if><if test='availableRate != null'>#{availableRate}, </if><if test='useSize != null'>#{useSize}, </if><if test='failSize != null'>#{failSize}, </if><if test='createTime != null'>#{createTime}, </if><if test='updateTime != null'>#{updateTime}, </if></trim></script>")
    int replace(ProxyIpEntity entity);

    /**
     * 根据唯一ID获取详细信息
     *
     * @param proxyId 唯一ID
     * @return 详细信息
     */
    ProxyIpDetailDTO getDetailById(@Param("proxyId") String proxyId);

    /**
     * 代理IP列表查询
     *
     * @param query 查询对象
     * @return 代理IP列表
     */
    List<ProxyIpListDTO> getList(@Param("query") ProxyIpListQuery query);

    /**
     * 代理IP分页查询
     *
     * @param pageInfo 分页控件
     * @param query    分页查询对象
     * @return 代理IP列表
     */
    IPage<ProxyIpListDTO> getPage(@Param("pg") Page<ProxyIpListDTO> pageInfo, @Param("query") ProxyIpPageQuery query);

    /**
     * 获取需要校验的代理IP
     * 代理池中可用的，校验次数小于10次的或者超过一周时间没有检验的
     */
    List<ProxyIpEntity> getValidateProxys();

    /**
     * 更新代理IP校验次数
     *
     * @param proxyId          唯一ID
     * @param validateCount    校验次数
     * @param availableCount   校验可用次数
     * @param unAvailableCount 校验不可用次数
     */
    void addValidateCount(@Param("proxyId") String proxyId, @Param("validateCount") int validateCount
            , @Param("availableCount") int availableCount, @Param("unAvailableCount") int unAvailableCount);
}
