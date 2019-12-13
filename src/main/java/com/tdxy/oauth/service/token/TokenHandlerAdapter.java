package com.tdxy.oauth.service.token;

import com.tdxy.oauth.exception.InvalidCodeException;
import com.tdxy.oauth.exception.InvalidTokenException;
import com.tdxy.oauth.model.bo.GetTokenParam;
import com.tdxy.oauth.model.po.Client;
import com.tdxy.oauth.model.po.Token;
import com.tdxy.oauth.service.token.type.TokenHandler;

/**
 * 已存在的各类token的handler处理器由于各自的实现差异，导致调用时的代码过于丑陋，
 * 所以这里引入适配器模式，使其适应上游统一的调用
 */
public interface TokenHandlerAdapter {
    Token handler(TokenHandler tokenHandler, Client client, GetTokenParam param) throws InvalidCodeException, InvalidTokenException;

    boolean support(String grantType);
}
