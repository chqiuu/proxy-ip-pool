package com.chqiuu.proxy.config.interceptor;


import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.lang.Nullable;
import org.springframework.web.servlet.AsyncHandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Slf4j
public class TimeConsumingInterceptor implements AsyncHandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //request方式
        request.setAttribute("_startTime", System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable ModelAndView modelAndView) throws Exception {
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, @Nullable Exception ex) throws Exception {
        Long endTime = System.currentTimeMillis();
        Long startTime = (Long) request.getAttribute("_startTime");
        log.info("{} {}；耗时：{}s", getIpAddress(request), getFullUrl(request), ((endTime - startTime) * 1.000) / 1000);
    }


    /**
     * 获取完整的URL路径
     *
     * @param request
     * @return
     * @author CHENQUAN
     * @date 2016年9月6日
     */
    private String getFullUrl(HttpServletRequest request) {
        //记录请求参数
        StringBuilder sb = new StringBuilder();
        String method = request.getMethod();
        sb.append(method).append(" ");
        sb.append(request.getRequestURL().toString());
        if ("POST".equals(method)) {
            //获取参数
            Map<String, String[]> pm = request.getParameterMap();
            Set<Map.Entry<String, String[]>> es = pm.entrySet();
            Iterator<Map.Entry<String, String[]>> iterator = es.iterator();
            int pointer = 0;
            while (iterator.hasNext()) {
                if (pointer == 0) {
                    sb.append("?");
                } else {
                    sb.append("&");
                }
                Map.Entry<String, String[]> next = iterator.next();
                String key = next.getKey();
                String[] value = next.getValue();
                for (int i = 0; i < value.length; i++) {
                    if (i != 0) {
                        sb.append("&");
                    }

                    String val = StrUtil.subPre(value[i], 20);
                    sb.append(key).append("=").append(val);
                }
                pointer++;
            }
        }
        return sb.toString();
    }

    /**
     * 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址;
     *
     * @param request
     * @return
     */
    private String getIpAddress(HttpServletRequest request) {
        // 获取请求主机IP地址,如果通过代理进来，则透过防火墙获取真实IP地址
        String ip = request.getHeader("X-Real-IP");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("WL-Proxy-Client-IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_CLIENT_IP");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("HTTP_X_FORWARDED_FOR");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getHeader("X-Forwarded-For");
            }
            if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
                ip = request.getRemoteAddr();
            }
            if (ip.equals("0:0:0:0:0:0:0:1")) {
                ip = "127.0.0.1";
            }
        }
        if (ip.length() > 15) {
            String[] ips = ip.split(",");
            for (String s : ips) {
                if (!("unknown".equalsIgnoreCase(s))) {
                    ip = s;
                    break;
                }
            }
        }
        return ip;
    }
}
