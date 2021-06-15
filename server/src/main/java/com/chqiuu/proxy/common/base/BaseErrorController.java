package com.chqiuu.proxy.common.base;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

import javax.servlet.RequestDispatcher;
import javax.servlet.http.HttpServletRequest;

/**
 * 错误页面统一处理
 *
 * @author chqiuu
 */
@Controller
public class BaseErrorController implements ErrorController {
    @GetMapping("/error")
    public String handleError(HttpServletRequest request) {
        Object status = request.getAttribute(RequestDispatcher.ERROR_STATUS_CODE);
        if (status != null) {
            HttpStatus httpStatus = HttpStatus.resolve(Integer.parseInt(status.toString()));
            switch (httpStatus != null ? httpStatus : HttpStatus.NOT_FOUND) {
                case NOT_FOUND:
                    // 发生404错误时，渲染error-404模板
                    return "error/404";
                case INTERNAL_SERVER_ERROR:
                    // 发生500错误时，渲染error-500模板
                    return "error/404";
                default:
                    // 未获取到错误类型时，渲染error模板
                    return "error/404";
            }
        }
        // 未获取到错误类型时，渲染error模板
        return "error/404";
    }

    @Override
    public String getErrorPath() {
        return "/error";
    }
}
