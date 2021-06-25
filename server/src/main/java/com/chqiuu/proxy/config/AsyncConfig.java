package com.chqiuu.proxy.config;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurer;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步线程池执行配置
 * 适用@EnableAsync注解开启异步任务支持
 *
 * @author chqiu
 */
@Slf4j
@EnableAsync
@Configuration
public class AsyncConfig implements AsyncConfigurer {
    /**
     * 定义线程池
     * 使用{@link java.util.concurrent.LinkedBlockingQueue}(FIFO）队列，是一个用于并发环境下的阻塞队列集合类
     * ThreadPoolTaskExecutor不是完全被IOC容器管理的bean,可以在方法上加上@Bean注解交给容器管理,这样可以将taskExecutor.initialize()方法调用去掉，容器会自动调用
     *
     * @return 线程池
     */
    @Bean("asyncExecutor")
    @Override
    public Executor getAsyncExecutor() {
        // Java虚拟机可用的处理器数
        int processors = Runtime.getRuntime().availableProcessors();
        // 定义线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(processors);
        // 线程池最大线程数,默认：40000
        taskExecutor.setMaxPoolSize(40000);
        // 线程队列最大线程数,默认：80000
        taskExecutor.setQueueCapacity(80000);
        // 线程名称前缀
        taskExecutor.setThreadNamePrefix("async-pool-");
        // 线程池中线程最大空闲时间，默认：60，单位：秒
        taskExecutor.setKeepAliveSeconds(60);
        //核心线程是否允许超时，默认:false
        taskExecutor.setWaitForTasksToCompleteOnShutdown(false);
        //IOC容器关闭时是否阻塞等待剩余的任务执行完成，即shutdown时会立即停止并终止当前正在执行任务，默认:false（必须设置setAwaitTerminationSeconds）
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        //阻塞IOC容器关闭的时间，默认：10秒（必须设置setWaitForTasksToCompleteOnShutdown）
        taskExecutor.setAwaitTerminationSeconds(10);
        /*
         * 指定被拒绝任务的处理方法，经过测试当并发量超过队列长度时可以继续执行，否则会抛出 org.springframework.core.task.TaskRejectedException异常
         * 拒绝策略，默认是AbortPolicy
         * AbortPolicy：丢弃任务并抛出RejectedExecutionException异常
         * DiscardPolicy：丢弃任务但不抛出异常
         * DiscardOldestPolicy：丢弃最旧的处理程序，然后重试，如果执行器关闭，这时丢弃任务
         * CallerRunsPolicy：执行器执行任务失败，则在策略回调方法中执行任务，如果执行器关闭，这时丢弃任务
         */
        taskExecutor.setRejectedExecutionHandler(new ThreadPoolExecutor.AbortPolicy());
        //初始化
        taskExecutor.initialize();
        return taskExecutor;
    }

    /**
     * 异步方法执行的过程中抛出的异常捕获
     *
     * @return 异常捕获Handler
     */
    @Override
    public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
        return (throwable, method, objects) -> {
            log.error("异步任务异常：方法：{} 参数：{}", method.getName(), JSON.toJSONString(objects));
            log.error(throwable.getMessage(), throwable);
        };
    }
}
