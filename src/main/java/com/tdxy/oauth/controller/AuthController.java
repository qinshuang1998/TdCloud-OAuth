package com.tdxy.oauth.controller;

import com.tdxy.oauth.Constant;
import com.tdxy.oauth.common.ApplicationContextUtil;
import com.tdxy.oauth.common.ResponseHelper;
import com.tdxy.oauth.exception.*;
import com.tdxy.oauth.model.bo.GetTokenParam;
import com.tdxy.oauth.model.po.Client;
import com.tdxy.oauth.model.po.Token;
import com.tdxy.oauth.model.bo.User;
import com.tdxy.oauth.service.ClientService;
import com.tdxy.oauth.service.token.DispatcherAdapter;
import com.tdxy.oauth.service.token.TokenHandlerAdapter;
import com.tdxy.oauth.service.token.TokenService;
import com.tdxy.oauth.service.token.type.TokenHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

/**
 * OAUTH授权控制器
 *
 * @author Qug_
 */
@Controller
@RequestMapping("/")
public class AuthController {
    /**
     * 第三方应用服务
     */
    private final ClientService clientService;

    /**
     * access_token服务
     */
    private final TokenService tokenService;

    @Autowired
    public AuthController(ClientService clientService, TokenService tokenService) {
        this.clientService = clientService;
        this.tokenService = tokenService;
    }

    /**
     * 用户授权
     *
     * @param responseType 固定值code，oauth的授权码模式
     * @param appId        应用appid
     * @param state        第三方应用传来的，保持一致
     * @param request      请求对象
     * @return auth.html视图
     */
    @RequestMapping(value = "/authorize", method = RequestMethod.GET)
    public ModelAndView authorize(
            @RequestParam(name = "response_type", required = false, defaultValue = "code") String responseType,
            @RequestParam(name = "app_id") String appId,
            @RequestParam(name = "state", required = false) String state,
            HttpServletRequest request) {
        // 获取Client实体
        Client client = null;
        try {
            client = clientService.getClient(appId);
        } catch (UnknownClientException e) {
            return new ModelAndView("error");
        }
        // 取得session中的User对象
        User user = (User) request.getSession().getAttribute(Constant.Session.SESSION_KEY);
        // 下发一个临时的授权码code
        String code = clientService.getCode(appId, user);
        // 构造回调地址
        String callback = client.getRedirectUri() + "?code=" + code + "&state=" + state;
        ModelAndView modelAndView = new ModelAndView("auth");
        modelAndView.addObject("client", client);
        modelAndView.addObject("callback", callback);
        return modelAndView;
    }

    /**
     * 获取access_token
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper token(@Valid GetTokenParam param, BindingResult bindingResult) {
        ResponseHelper<Token> result = new ResponseHelper<>();
        if (bindingResult.hasErrors()) {
            return result.sendError("请传入合法的参数");
        }
        ApplicationContext ac = ApplicationContextUtil.getApplicationContext();
        TokenHandler tokenHandler = ac.getBean(param.getGrantType(), TokenHandler.class);
        Token token;
        try {
            // 检查客户端合法性
            Client client = tokenService.checkClient(param.getAppId(), param.getAppKey());
            TokenHandlerAdapter adapter = DispatcherAdapter.getAdapter(param.getGrantType());
            token = adapter.handler(tokenHandler, client, param);
        } catch (IllegalClientException | GrantTypeException | InvalidCodeException | InvalidTokenException e) {
            return result.sendError(e.getMessage());
        }
        return result.sendSuccess(token);
    }
}
