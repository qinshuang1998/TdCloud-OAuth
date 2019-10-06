package com.tdxy.oauth.interceptor;

import com.tdxy.oauth.Constant;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.net.URLEncoder;

/**
 * 登录态拦截器
 *
 * @author Qug_
 */
public class AccessInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
        if (request.getSession().getAttribute(Constant.Session.SESSION_KEY) == null) {
            String callBack = URLEncoder.encode(request.getRequestURL() + "?" + request.getQueryString(), "utf-8");
            response.setStatus(Constant.HttpStatus.REDIRECT);
            response.sendRedirect("/login?from=" + callBack);
            return false;
        }
        return true;
    }
}
