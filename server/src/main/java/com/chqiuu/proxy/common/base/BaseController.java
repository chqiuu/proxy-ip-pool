package com.chqiuu.proxy.common.base;

import cn.hutool.extra.servlet.ServletUtil;
import com.chqiuu.proxy.common.constant.Constant;
import com.chqiuu.proxy.common.enums.UserRoleEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

/**
 * Controller基类
 *
 * @author chqiu
 */
@Slf4j
public class BaseController {

    /**
     * 获取当前用户请求对象
     *
     * @return 当前用户请求对象
     */
    public HttpServletRequest getRequest() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == requestAttributes) {
            return null;
        }
        return requestAttributes.getRequest();
    }

    /**
     * 获取客户端IP地址
     *
     * @return 客户端IP地址
     */
    public String getClientIp() {
        return ServletUtil.getClientIP(getRequest(), "");
    }

    /**
     * 获取当前用户响应对象
     *
     * @return 当前用户响应对象
     */
    public HttpServletResponse getResponse() {
        ServletRequestAttributes requestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        if (null == requestAttributes) {
            return null;
        }
        return requestAttributes.getResponse();
    }

    /**
     * 获取当前用户Session
     *
     * @return 当前用户Session
     */
    public HttpSession getSession() {
        HttpServletRequest request = getRequest();
        if (null == request) {
            return null;
        }
        return request.getSession();
    }
    /**
     * 获取用户所拥有的权限列表
     *
     * @return 用户所拥有的权限列表1
     */
    public List<String> getAuthentication() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        List<String> list = new ArrayList<>();
        for (GrantedAuthority grantedAuthority : authorities) {
            list.add(grantedAuthority.getAuthority());
        }
        return list;
    }

    /**
     * 判断用户是否具有当期角色
     *
     * @param role 角色
     * @return 是否具有当前角色
     */
    public boolean hasRole(UserRoleEnum role) {
        // get security context from thread local
        SecurityContext context = SecurityContextHolder.getContext();
        if (context == null) {
            return false;
        }

        Authentication authentication = context.getAuthentication();
        if (authentication == null) {
            return false;
        }

        for (GrantedAuthority auth : authentication.getAuthorities()) {
            if (role.getRole().equals(auth.getAuthority())) {
                return true;
            }
        }
        return false;
    }
}
