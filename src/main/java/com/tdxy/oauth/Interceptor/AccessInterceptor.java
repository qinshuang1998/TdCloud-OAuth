package com.tdxy.oauth.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URLEncoder;

/**
 * 登录态拦截器
 *
 * @author Qug_
 */
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getSession().getAttribute("userId") == null) {
            String callBack = URLEncoder.encode(request.getRequestURL() + "?" + request.getQueryString(), "utf-8");
            response.setStatus(302);
            response.sendRedirect("/login?from=" + callBack);
            return false;
        }
        return true;
    }
}
