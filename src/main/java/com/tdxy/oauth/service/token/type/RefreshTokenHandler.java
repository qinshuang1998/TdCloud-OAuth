package com.tdxy.oauth.service.token.type;

import com.tdxy.oauth.Constant;
import com.tdxy.oauth.exception.InvalidTokenException;
import com.tdxy.oauth.model.po.Token;
import com.tdxy.oauth.service.token.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("refresh_token")
public class RefreshTokenHandler implements TokenHandler {
    private final TokenService tokenService;

    @Autowired
    public RefreshTokenHandler(TokenService tokenService) {
        this.tokenService = tokenService;
    }

    public Token refresh(String refreshToken) throws InvalidTokenException {
        return tokenService.refreshToken(refreshToken, Constant.Token.EXPIRE_TIME_SEC);
    }
}
