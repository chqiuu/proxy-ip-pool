package com.chqiuu.proxy.config;

import cn.hutool.core.date.DatePattern;
import com.chqiuu.proxy.config.converter.StringToDateConverter;
import com.chqiuu.proxy.config.converter.StringToIntegerConverter;
import com.chqiuu.proxy.config.converter.StringToLongConverter;
import com.chqiuu.proxy.config.converter.StringToMapConverter;
import com.chqiuu.proxy.config.interceptor.TimeConsumingInterceptor;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import com.fasterxml.jackson.datatype.jdk8.Jdk8Module;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalTimeSerializer;
import com.fasterxml.jackson.module.paramnames.ParameterNamesModule;
import lombok.extern.slf4j.Slf4j;
import org.apache.http.conn.ssl.NoopHostnameVerifier;
import org.apache.http.conn.ssl.SSLConnectionSocketFactory;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.ssl.TrustStrategy;
import org.springframework.boot.system.ApplicationHome;
import org.springframework.boot.web.servlet.MultipartConfigFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.format.FormatterRegistry;
import org.springframework.http.client.ClientHttpRequestFactory;
import org.springframework.http.client.HttpComponentsClientHttpRequestFactory;
import org.springframework.util.unit.DataSize;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.net.ssl.SSLContext;
import javax.servlet.MultipartConfigElement;
import java.io.File;
import java.security.KeyManagementException;
import java.security.KeyStoreException;
import java.security.NoSuchAlgorithmException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.TimeZone;

@Slf4j
@Configuration
public class WebMvcConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // ??????????????????
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
    }

    /**
     * Controller ????????????????????????
     *
     * @param registry ??????????????????
     */
    @Override
    public void addFormatters(FormatterRegistry registry) {
        registry.addConverter(new StringToDateConverter());
        registry.addConverter(new StringToIntegerConverter());
        registry.addConverter(new StringToLongConverter());
        registry.addConverter(new StringToMapConverter());
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new TimeConsumingInterceptor()).addPathPatterns("/**")
                .excludePathPatterns("/swagger/**", "/static/**", "/resource/**");
    }
    /**
     * ??????????????????JSON?????????????????????
     *
     * @return ???????????????ObjectMapper
     */
    @Bean
    public ObjectMapper objectMapper() {
        ObjectMapper mapper = new ObjectMapper();
        // ??????????????????
        mapper.configure(SerializationFeature.INDENT_OUTPUT, true);
        // ???????????????????????????
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);
        // ??????Date???????????????
        DateFormat dateFormat = new SimpleDateFormat(DatePattern.NORM_DATETIME_PATTERN);
        mapper.setDateFormat(dateFormat);

        JavaTimeModule javaTimeModule = new JavaTimeModule();
        // ????????????json???,????????????long??????string ?????????Long?????????Json?????????????????????
        javaTimeModule.addSerializer(Long.class, ToStringSerializer.instance);
        javaTimeModule.addSerializer(Long.TYPE, ToStringSerializer.instance);

        // ??????LocalDateTime???????????????
        javaTimeModule.addSerializer(LocalDateTime.class, new LocalDateTimeSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")));
        javaTimeModule.addSerializer(LocalDate.class, new LocalDateSerializer(DateTimeFormatter.ofPattern("yyyy-MM-dd")));
        javaTimeModule.addSerializer(LocalTime.class, new LocalTimeSerializer(DateTimeFormatter.ofPattern("HH:mm:ss")));

        mapper.registerModule(javaTimeModule);
        mapper.setTimeZone(TimeZone.getTimeZone("GMT+8"));

        mapper.registerModule(new ParameterNamesModule()).registerModule(new Jdk8Module()).registerModule(new JavaTimeModule());

        mapper.enable(DeserializationFeature.ACCEPT_EMPTY_STRING_AS_NULL_OBJECT);

        // ????????????List
        mapper.configure(DeserializationFeature.ACCEPT_SINGLE_VALUE_AS_ARRAY, true);
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        // ????????????????????????
        mapper.configure(SerializationFeature.ORDER_MAP_ENTRIES_BY_KEYS, true);
        mapper.configure(MapperFeature.ALLOW_COERCION_OF_SCALARS, true);
        mapper.configure(MapperFeature.SORT_PROPERTIES_ALPHABETICALLY, true);
        return mapper;
    }

    /**
     * ????????????????????????
     *
     * @return MultipartConfigElement
     */
    @Bean
    public MultipartConfigElement multipartConfigElement() {
        // ?????????????????? ?????? The temporary upload location [] is not valid ????????????
        String multipartCache = String.format("%s%scache", new ApplicationHome(getClass()).getSource().getParentFile().toString(), File.separator);
        File file = new File(multipartCache);
        file.setWritable(true, false);
        if (!file.exists()) {
            file.mkdirs();
        }
        MultipartConfigFactory factory = new MultipartConfigFactory();
        factory.setLocation(multipartCache);
        // ?????????????????? KB,MB
        factory.setMaxFileSize(DataSize.ofGigabytes(1L));
        // ??????????????????????????????
        factory.setMaxRequestSize(DataSize.ofGigabytes(1L));
        return factory.createMultipartConfig();
    }


    /**
     * ??????SSL
     *
     * @return ??????SSL ClientHttpRequestFactory
     * @throws KeyStoreException        KeyStoreException
     * @throws NoSuchAlgorithmException NoSuchAlgorithmException
     * @throws KeyManagementException   KeyManagementException
     */
    @Bean
    public ClientHttpRequestFactory getClientHttpRequestFactory() throws KeyStoreException, NoSuchAlgorithmException, KeyManagementException {
        TrustStrategy acceptingTrustStrategy = (x509Certificates, s) -> true;
        SSLContext sslContext = org.apache.http.ssl.SSLContexts.custom().loadTrustMaterial(null, acceptingTrustStrategy).build();
        SSLConnectionSocketFactory csf = new SSLConnectionSocketFactory(sslContext, new NoopHostnameVerifier());
        CloseableHttpClient httpClient = HttpClients.custom().setSSLSocketFactory(csf).build();
        HttpComponentsClientHttpRequestFactory requestFactory = new HttpComponentsClientHttpRequestFactory();
        requestFactory.setHttpClient(httpClient);
        return requestFactory;
    }

    @Bean
    public RestTemplate restTemplate(ClientHttpRequestFactory factory) {
        return new RestTemplate(factory);
    }
}
