package com.chqiuu.proxy.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;
import org.springframework.scheduling.concurrent.ThreadPoolTaskScheduler;

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
public class AsyncConfig {
    /**
     * 多定时任务同步执行线程池
     *
     * @return 线程池对象
     */
    @Bean
    public TaskScheduler taskScheduler() {
        ThreadPoolTaskScheduler taskExecutor = new ThreadPoolTaskScheduler();
        taskExecutor.setPoolSize(30);
        return taskExecutor;
    }

    /**
     * 验证代理IP的线程池
     *
     * @return 线程池
     */
    @Bean("validateProxyAsyncExecutor")
    public ThreadPoolTaskExecutor getValidateProxyAsyncExecutor() {
        // Java虚拟机可用的处理器数
        int processors = Runtime.getRuntime().availableProcessors();
        // 定义线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(processors * 2);
        // 线程池最大线程数,默认：40000
        taskExecutor.setMaxPoolSize(processors * 3);
        // 线程队列最大线程数,默认：80000
        taskExecutor.setQueueCapacity(processors * 10000);
        // 线程名称前缀
        taskExecutor.setThreadNamePrefix("async-pool-validate");
        // 如果设置为 true，那么核心线程数也将受keepAliveTime控制
        taskExecutor.setAllowCoreThreadTimeOut(true);
        // 线程池中线程最大空闲时间，默认：60，单位：秒
        taskExecutor.setKeepAliveSeconds(60);
        // IOC容器关闭时是否阻塞等待剩余的任务执行完成，即shutdown时会立即停止并终止当前正在执行任务，默认:false（必须设置setAwaitTerminationSeconds）
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 阻塞IOC容器关闭的时间，默认：10秒（必须设置setWaitForTasksToCompleteOnShutdown）
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
        // 初始化
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean("validateNewsProxyIpAsyncExecutor")
    public ThreadPoolTaskExecutor validateNewsProxyIpAsyncExecutor() {
        // Java虚拟机可用的处理器数
        int processors = Runtime.getRuntime().availableProcessors();
        // 定义线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(processors * 2);
        // 线程池最大线程数,默认：40000
        taskExecutor.setMaxPoolSize(processors * 3);
        // 线程队列最大线程数,默认：80000
        taskExecutor.setQueueCapacity(processors * 10000);
        // 线程名称前缀
        taskExecutor.setThreadNamePrefix("async-validate-new");
        // 如果设置为 true，那么核心线程数也将受keepAliveTime控制
        taskExecutor.setAllowCoreThreadTimeOut(true);
        // 线程池中线程最大空闲时间，默认：60，单位：秒
        taskExecutor.setKeepAliveSeconds(60);
        // IOC容器关闭时是否阻塞等待剩余的任务执行完成，即shutdown时会立即停止并终止当前正在执行任务，默认:false（必须设置setAwaitTerminationSeconds）
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 阻塞IOC容器关闭的时间，默认：10秒（必须设置setWaitForTasksToCompleteOnShutdown）
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
        // 初始化
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean("validateAvailableProxyIpAsyncExecutor")
    public ThreadPoolTaskExecutor validateAvailableProxyIpAsyncExecutor() {
        // Java虚拟机可用的处理器数
        int processors = Runtime.getRuntime().availableProcessors();
        // 定义线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(processors * 2);
        // 线程池最大线程数,默认：40000
        taskExecutor.setMaxPoolSize(processors * 3);
        // 线程队列最大线程数,默认：80000
        taskExecutor.setQueueCapacity(processors * 10000);
        // 线程名称前缀
        taskExecutor.setThreadNamePrefix("async-validate-available");
        // 如果设置为 true，那么核心线程数也将受keepAliveTime控制
        taskExecutor.setAllowCoreThreadTimeOut(true);
        // 线程池中线程最大空闲时间，默认：60，单位：秒
        taskExecutor.setKeepAliveSeconds(60);
        // IOC容器关闭时是否阻塞等待剩余的任务执行完成，即shutdown时会立即停止并终止当前正在执行任务，默认:false（必须设置setAwaitTerminationSeconds）
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 阻塞IOC容器关闭的时间，默认：10秒（必须设置setWaitForTasksToCompleteOnShutdown）
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
        // 初始化
        taskExecutor.initialize();
        return taskExecutor;
    }

    @Bean("validateUnavailableProxyIpAsyncExecutor")
    public ThreadPoolTaskExecutor validateUnavailableProxyIpAsyncExecutor() {
        // Java虚拟机可用的处理器数
        int processors = Runtime.getRuntime().availableProcessors();
        // 定义线程池
        ThreadPoolTaskExecutor taskExecutor = new ThreadPoolTaskExecutor();
        // 核心线程数
        taskExecutor.setCorePoolSize(processors * 2);
        // 线程池最大线程数,默认：40000
        taskExecutor.setMaxPoolSize(processors * 3);
        // 线程队列最大线程数,默认：80000
        taskExecutor.setQueueCapacity(processors * 10000);
        // 线程名称前缀
        taskExecutor.setThreadNamePrefix("async-validate-unavailable");
        // 如果设置为 true，那么核心线程数也将受keepAliveTime控制
        taskExecutor.setAllowCoreThreadTimeOut(true);
        // 线程池中线程最大空闲时间，默认：60，单位：秒
        taskExecutor.setKeepAliveSeconds(60);
        // IOC容器关闭时是否阻塞等待剩余的任务执行完成，即shutdown时会立即停止并终止当前正在执行任务，默认:false（必须设置setAwaitTerminationSeconds）
        taskExecutor.setWaitForTasksToCompleteOnShutdown(true);
        // 阻塞IOC容器关闭的时间，默认：10秒（必须设置setWaitForTasksToCompleteOnShutdown）
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
        // 初始化
        taskExecutor.initialize();
        return taskExecutor;
    }

}
