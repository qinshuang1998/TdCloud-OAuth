package com.tdxy.oauth.service.token;

import com.tdxy.oauth.exception.IllegalClientException;
import com.tdxy.oauth.exception.InvalidCodeException;
import com.tdxy.oauth.model.bo.GetTokenParam;
import com.tdxy.oauth.model.po.Client;
import com.tdxy.oauth.model.po.Token;
import com.tdxy.oauth.service.token.type.AuthorizationCodeHandler;
import com.tdxy.oauth.service.token.type.TokenHandler;
import org.springframework.stereotype.Component;

@Component
public class AuthorizationCodeHandlerAdapter implements TokenHandlerAdapter {

    @Override
    public Token handler(TokenHandler tokenHandler, Client client, GetTokenParam param) throws InvalidCodeException {
        return ((AuthorizationCodeHandler) tokenHandler).authorization(client, param.getCode());
    }

    @Override
    public boolean support(String grantType) {
        return "authorization_code".equals(grantType);
    }
}
