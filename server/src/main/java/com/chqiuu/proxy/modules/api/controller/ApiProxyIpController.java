package com.chqiuu.proxy.modules.api.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.chqiuu.proxy.modules.api.dto.ProxyIpCommonListDTO;
import com.chqiuu.proxy.modules.api.query.ProxyIpCommonPageQuery;
import com.chqiuu.proxy.common.base.BaseController;
import com.chqiuu.proxy.common.domain.Result;
import com.chqiuu.proxy.modules.pool.service.ProxyIpService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 代理IP API接口
 *
 * @author chqiu
 */
@Validated
@RestController
@RequestMapping("/api/pool")
@Api(value = "代理IP API接口", tags = "代理IP API接口")
@AllArgsConstructor
public class ApiProxyIpController extends BaseController {

    private final ProxyIpService proxyIpService;

    @ApiOperation(value = "代理IP分页查询", notes = "代理IP分页查询")
    @GetMapping("/page")
    public Result<IPage<ProxyIpCommonListDTO>> page(ProxyIpCommonPageQuery query) {
        return Result.ok(proxyIpService.getCommonPage(query));
    }
}
