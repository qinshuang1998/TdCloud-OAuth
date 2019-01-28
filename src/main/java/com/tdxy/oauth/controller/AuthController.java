package com.tdxy.oauth.controller;

import com.tdxy.oauth.OauthSystem;
import com.tdxy.oauth.component.ResponseHelper;
import com.tdxy.oauth.exception.UnknownClientException;
import com.tdxy.oauth.model.entity.Client;
import com.tdxy.oauth.model.entity.Token;
import com.tdxy.oauth.model.entity.User;
import com.tdxy.oauth.service.ClientService;
import com.tdxy.oauth.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

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
        ModelAndView modelAndView = new ModelAndView();
        try {
            // 取得session中的User对象
            User user = (User) request.getSession().getAttribute(OauthSystem.Session.SESSION_KEY);
            // 获取Client实体
            Client client = this.clientService.getClient(appId);
            // 下发一个临时的授权码code
            String code = this.clientService.getCode(appId, user);
            // 构造回调地址
            String callback = client.getRedirectUri() + "?code=" + code + "&state=" + state;
            modelAndView.setViewName("auth");
            modelAndView.addObject("client", client);
            modelAndView.addObject("callback", callback);
        } catch (UnknownClientException ex) {
            modelAndView.setViewName("error");
        }
        return modelAndView;
    }

    /**
     * 获取access_token
     *
     * @param grantType
     * @param appId
     * @param appKey
     * @param code
     * @param refreshToken
     * @return
     */
    @RequestMapping(value = "/token", method = RequestMethod.GET)
    @ResponseBody
    public ResponseHelper token(
            @RequestParam(name = "grant_type") String grantType,
            @RequestParam(name = "app_id") String appId,
            @RequestParam(name = "app_key") String appKey,
            @RequestParam(name = "code", required = false) String code,
            @RequestParam(name = "refresh_token", required = false) String refreshToken) {
        ResponseHelper<Token> result = new ResponseHelper<>();
        Token token;
        try {
            // 检查客户端合法性
            Client client = this.tokenService.checkClient(appId, appKey);
            switch (grantType) {
                case "authorization_code":
                    // 检查授权码code的合法性
                    User user = this.tokenService.checkCode(client.getAppId(), code);
                    // 授权码合法的话就下发最终的Token
                    token = this.tokenService.getToken(client, user, OauthSystem.Token.EXPIRE_TIME_SEC);
                    break;
                case "refresh_token":
                    token = this.tokenService.refreshToken(refreshToken, OauthSystem.Token.EXPIRE_TIME_SEC);
                    break;
                default:
                    return result.sendError("无效的grant_type");
            }
        } catch (Exception ex) {
            return result.sendError(ex.getMessage());
        }
        return result.sendSuccess(token);
    }
}
