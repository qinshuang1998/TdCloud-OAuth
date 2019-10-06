package com.tdxy.oauth.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.tdxy.oauth.Constant;
import com.tdxy.oauth.common.ResponseHelper;
import com.tdxy.oauth.context.AuthUserContext;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class ApiEndPointInterceptor implements HandlerInterceptor {
    private final static Logger logger = LoggerFactory.getLogger(ApiEndPointInterceptor.class);

    @Autowired
    private AuthUserContext authUserContext;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) {
        if (this.authUserContext.isAuthenticated()) {
            return true;
        }
        PrintWriter writer = null;
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json;charset=UTF-8");
        response.setStatus(Constant.HttpStatus.UNAUTHORIZED);
        ResponseHelper result = new ResponseHelper();
        String error = JSONObject.toJSONString(result.sendError("无效的access_token"));
        try {
            writer = response.getWriter();
            writer.print(error);
        } catch (IOException ex) {
            logger.error("response error", ex);
        }
        return false;
    }
}
