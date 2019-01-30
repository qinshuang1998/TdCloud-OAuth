package com.tdxy.oauth.interceptor;

import com.tdxy.oauth.OauthSystem;
import com.tdxy.oauth.context.AuthUserContext;
import com.tdxy.oauth.model.entity.User;
import com.tdxy.oauth.service.TokenService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ApiHeaderInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(ApiHeaderInterceptor.class);

    @Autowired
    private AuthUserContext authUserContext;

    @Autowired
    private TokenService tokenService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (this.authUserContext.isAuthenticated()) {
            return true;
        }
        if (request.getHeader(OauthSystem.Token.HEADER) != null) {
            String token = request.getHeader(OauthSystem.Token.HEADER).substring(OauthSystem.Token.HEAD.length());
            User user = null;
            try {
                user = this.tokenService.getUserByToken(token);
                this.authUserContext.setPrincipal(user);
            } catch (Exception ex) {
                logger.debug("Authorization头传递的token验证失败");
            }
        }
        return true;
    }
}
