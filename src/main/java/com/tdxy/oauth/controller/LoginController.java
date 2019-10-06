package com.tdxy.oauth.controller;

import com.tdxy.oauth.Constant;
import com.tdxy.oauth.common.ResponseHelper;
import com.tdxy.oauth.service.login.factory.LoginStrategyFactory;
import com.tdxy.oauth.model.bo.LoginResult;
import com.tdxy.oauth.service.login.LoginStrategy;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录控制器
 *
 * @author Qug_
 */
@Controller
@RequestMapping("/")
public class LoginController {
    /**
     * 用户登录视图
     *
     * @param referer 回调地址
     * @return login.html视图
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(name = "from") String referer,
                              HttpServletRequest request) {
        if (request.getSession().getAttribute(Constant.Session.SESSION_KEY) != null) {
            return new ModelAndView("redirect:" + referer);
        }
        ModelAndView modelAndView = new ModelAndView("login");
        modelAndView.addObject("referer", referer);
        return modelAndView;
    }

    /**
     * 用户登录的表单提交接口
     *
     * @param username 用户名
     * @param password 密码
     * @param role     用户角色
     * @param request  请求对象
     * @return 登录结果Json串
     */
    @RequestMapping(value = "/doLogin", method = RequestMethod.POST)
    @ResponseBody
    public ResponseHelper doLogin(@RequestParam(name = "username") String username,
                                  @RequestParam(name = "password") String password,
                                  @RequestParam(name = "role") String role,
                                  HttpServletRequest request) {
        ResponseHelper<String> result = new ResponseHelper<>();
        // 登录策略
        LoginStrategy loginStrategy = LoginStrategyFactory.getStrategy(role);
        LoginResult loginResult = loginStrategy.login(username, password, role);
        if (loginResult.isSuccess()) {
            request.getSession().setAttribute(Constant.Session.SESSION_KEY, loginResult.getUser());
            return result.sendSuccess();
        }
        return result.sendError("当前登录失败");
    }
}
