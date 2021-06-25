package com.chqiuu.proxy.modules.pool.controller;

import java.util.List;

import com.chqiuu.proxy.modules.pool.entity.ProxyIpEntity;
import com.chqiuu.proxy.modules.pool.query.ProxyIpListQuery;
import com.chqiuu.proxy.modules.pool.query.ProxyIpPageQuery;
import com.chqiuu.proxy.modules.pool.service.ProxyIpService;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import com.chqiuu.proxy.common.base.BaseController;
import com.chqiuu.proxy.common.domain.Result;
import com.chqiuu.proxy.common.domain.ResultEnum;

import com.chqiuu.proxy.modules.pool.vo.ProxyIpInputVO;
import com.chqiuu.proxy.modules.pool.dto.ProxyIpDetailDTO;
import com.chqiuu.proxy.modules.pool.dto.ProxyIpListDTO;
import com.chqiuu.proxy.common.validator.group.Default;
import com.chqiuu.proxy.common.validator.group.Create;
import com.chqiuu.proxy.common.validator.group.Update;

import javax.validation.constraints.NotNull;

import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 * 代理IP请求处理层
 *
 * @author chqiuu
 * @date 2021年6月11日
 */
@Validated
@RestController
@RequestMapping("/pool/ip")
@Api(value = "代理IP", tags = "代理IP")
@AllArgsConstructor
public class ProxyIpController extends BaseController {

    private final ProxyIpService proxyIpService;

    @ApiOperation(value = "根据唯一ID获取详细信息", notes = "根据唯一ID获取详细信息")
    @GetMapping("/detail/{proxyId}")
    public Result<ProxyIpDetailDTO> detail(@PathVariable("proxyId") @NotNull(message = "唯一ID不能为空") String proxyId) {
        return Result.ok(proxyIpService.getDetailById(proxyId));
    }

    @ApiOperation(value = "代理IP列表查询", notes = "代理IP列表查询")
    @GetMapping("/list")
    public Result<List<ProxyIpListDTO>> list(ProxyIpListQuery query) {
        return Result.ok(proxyIpService.getList(query));
    }

    @ApiOperation(value = "代理IP分页查询", notes = "代理IP分页查询")
    @GetMapping("/page")
    public Result<IPage<ProxyIpListDTO>> page(ProxyIpPageQuery query) {
        return Result.ok(proxyIpService.getPage(query));
    }

    @ApiOperation(value = "新建代理IP", notes = "新建代理IP，返回ID")
    @PostMapping("/add")
    public Result<String> add(@Validated({Create.class}) @RequestBody ProxyIpInputVO vo) {
        ProxyIpEntity entity = vo.convertToEntity();
        entity.setProxyId(null);
        proxyIpService.save(entity);
        return Result.ok(entity.getProxyId());
    }

    @ApiOperation(value = "更新代理IP", notes = "更新代理IP")
    @PostMapping("/update")
    public Result<String> update(@Validated({Update.class}) @RequestBody ProxyIpInputVO vo) {
        ProxyIpEntity entity = proxyIpService.getById(vo.getProxyId());
        if (null == entity) {
            return Result.failed(ResultEnum.NOT_FOUND, "没有找到需要更新的记录");
        }
        proxyIpService.updateById(vo.convertToEntity());
        return Result.ok();
    }

    @ApiOperation(value = "根据唯一ID删除代理IP", notes = "根据唯一ID删除代理IP")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "proxyId", value = "唯一ID", paramType = "path"),
    })
    @PostMapping("/delete/{proxyId}")
    public Result<Boolean> delete(@PathVariable("proxyId") @NotNull(message = "唯一ID不能为空") String proxyId) {
        ProxyIpEntity entity = proxyIpService.getById(proxyId);
        if (null == entity) {
            return Result.failed(ResultEnum.NOT_FOUND, "没有找到需要删除的记录");
        }
        //TODO 其他限制删除条件
        return Result.ok(proxyIpService.removeById(proxyId));
    }
}
