package com.tdxy.oauth.service.token;

import com.tdxy.oauth.exception.InvalidCodeException;
import com.tdxy.oauth.exception.InvalidTokenException;
import com.tdxy.oauth.model.bo.GetTokenParam;
import com.tdxy.oauth.model.po.Client;
import com.tdxy.oauth.model.po.Token;
import com.tdxy.oauth.service.token.type.TokenHandler;

public interface TokenHandlerAdapter {
    Token handler(TokenHandler tokenHandler, Client client, GetTokenParam param) throws InvalidCodeException, InvalidTokenException;

    boolean support(String grantType);
}
