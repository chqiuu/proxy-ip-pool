package com.chqiuu.proxy.config;

import com.baomidou.mybatisplus.extension.plugins.MybatisPlusInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.OptimisticLockerInnerInterceptor;
import com.baomidou.mybatisplus.extension.plugins.inner.PaginationInnerInterceptor;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Mybatis配置
 *
 * @author chqiu
 */
@Configuration(proxyBeanMethods = false)
@MapperScan("com.chqiuu.proxy.**.mapper")
public class MybatisConfig {
    /**
     * 3.4.0 以后的配置方式
     */
    @Bean
    public MybatisPlusInterceptor mybatisPlusInterceptor() {
        MybatisPlusInterceptor interceptor = new MybatisPlusInterceptor();
        // 乐观锁
        interceptor.addInnerInterceptor(new OptimisticLockerInnerInterceptor());
        // 分页配置
        interceptor.addInnerInterceptor(new PaginationInnerInterceptor());
        return interceptor;
    }
}