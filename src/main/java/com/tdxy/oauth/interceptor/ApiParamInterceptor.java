package com.tdxy.oauth.interceptor;

import com.tdxy.oauth.context.AuthUserContext;
import com.tdxy.oauth.model.bo.User;
import com.tdxy.oauth.service.token.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiParamInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(ApiParamInterceptor.class);

    @Autowired
    private AuthUserContext authUserContext;

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (this.authUserContext.isAuthenticated()) {
            return true;
        }
        String token = request.getParameter("access_token");
        if (token != null) {
            try {
                User user = this.tokenService.getUserByToken(token);
                this.authUserContext.setPrincipal(user);
            } catch (Exception ex) {
                logger.debug("GET参数传递的token验证失败");
            }
        }
        return true;
    }
}
