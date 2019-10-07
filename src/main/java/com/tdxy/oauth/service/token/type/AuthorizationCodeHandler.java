package com.tdxy.oauth.service.token.type;

import com.tdxy.oauth.Constant;
import com.tdxy.oauth.exception.IllegalClientException;
import com.tdxy.oauth.exception.InvalidCodeException;
import com.tdxy.oauth.model.bo.User;
import com.tdxy.oauth.model.po.Client;
import com.tdxy.oauth.model.po.Token;
import com.tdxy.oauth.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("authorization_code")
public class AuthorizationCodeHandler implements TokenHandler {

    private final TokenService tokenService;

    @Autowired
    public AuthorizationCodeHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public Token authorization(Client client, String code) throws InvalidCodeException {
        // 检查授权码code的合法性
        User user = tokenService.checkCode(client.getAppId(), code);
        // 授权码合法的话就下发最终的Token
        return tokenService.getToken(client, user, Constant.Token.EXPIRE_TIME_SEC);
    }
}
