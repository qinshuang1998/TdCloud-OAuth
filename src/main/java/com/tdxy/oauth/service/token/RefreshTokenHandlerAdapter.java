package com.tdxy.oauth.service.token;

import com.tdxy.oauth.exception.InvalidTokenException;
import com.tdxy.oauth.model.bo.GetTokenParam;
import com.tdxy.oauth.model.po.Client;
import com.tdxy.oauth.model.po.Token;
import com.tdxy.oauth.service.token.type.RefreshTokenHandler;
import com.tdxy.oauth.service.token.type.TokenHandler;
import org.springframework.stereotype.Component;

@Component
public class RefreshTokenHandlerAdapter implements TokenHandlerAdapter {
    @Override
    public Token handler(TokenHandler tokenHandler, Client client, GetTokenParam param) throws InvalidTokenException {
        return ((RefreshTokenHandler) tokenHandler).refresh(param.getRefresh_token());
    }

    @Override
    public boolean support(String grantType) {
        return "refresh_token".equals(grantType);
    }
}
