package com.tdxy.oauth.model.dao;

import com.tdxy.oauth.model.bo.RefreshToken;
import com.tdxy.oauth.model.mapper.RefreshTokenMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class RefreshTokenDao {
    @Autowired
    private RefreshTokenMapper refreshTokenMapper;

    public RefreshToken findByAppIdAndUser(String appId, String userIdentity) {
        return this.refreshTokenMapper.findByAppIdAndUser(appId, userIdentity);
    }

    public void addOne(RefreshToken refreshToken) {
        this.refreshTokenMapper.addOne(refreshToken);
    }

    public void updateByTokenId(String tokenId, String refreshToken) {
        this.refreshTokenMapper.updateByTokenId(tokenId, refreshToken);
    }

    public RefreshToken findTokenByRefreshToken(String refreshToken) {
        return this.refreshTokenMapper.findTokenByRefreshToken(refreshToken);
    }
}
