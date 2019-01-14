package com.tdxy.oauth.controller;

import com.tdxy.oauth.component.ResponseHelper;
import com.tdxy.oauth.model.entity.User;
import com.tdxy.oauth.model.entity.ZfCookie;
import com.tdxy.oauth.service.ZfService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户登录控制器
 * <p>
 * ZfService作为有状态Bean，
 * 要么控制同步，要么控制器多例，要么Service多例
 * 单例Bean依赖多例Bean切记采用方法注入依赖，否则并发下数据会互窜
 * =============================================
 * 以上方案不太好，我还是尽量避免有状态Bean的出现
 * 所以把ZfService的cookie信息以参数传递
 * Spring和Struts控制器区别就是Struts是默认多例的
 *
 * @author Qug_
 */
@Controller
@RequestMapping("/")
public class LoginController {
    /**
     * 正方服务
     */
    private ZfService zfService;

    @Autowired
    public LoginController(ZfService zfService) {
        this.zfService = zfService;
    }

    /**
     * 用户登录视图
     *
     * @param referer 回调地址
     * @return login.html视图
     */
    @RequestMapping(value = "/login", method = RequestMethod.GET)
    public ModelAndView login(@RequestParam(name = "from") String referer) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.setViewName("login");
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
        ResponseHelper<Object> result = new ResponseHelper<>();
        try {
            switch (role) {
                case "student":
                    ZfCookie cookie = this.zfService.getCookie(username);
                    int cookieStatus = this.zfService.checkCookie(cookie);
                    boolean isSuccess = (cookieStatus == 1) || this.zfService.doLogin(username, password);
                    if (isSuccess) {
                        // 用来标识用户，包含用户角色和用户身份
                        User user = new User("student", username);
                        request.getSession().setAttribute("userId", user);
                        result = result.sendSuccess();
                    } else {
                        result = result.sendError("正方认证失败");
                    }
                    return result;
                case "teacher":
                    break;
                default:
                    break;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return result.sendError("未知错误");
    }
}
