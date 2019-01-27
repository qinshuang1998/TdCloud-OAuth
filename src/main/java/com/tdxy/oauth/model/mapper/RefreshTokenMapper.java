package com.tdxy.oauth.model.mapper;

import com.tdxy.oauth.model.entity.RefreshToken;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface RefreshTokenMapper {
    @Select("SELECT * FROM oauth_refresh_token WHERE app_id = #{arg0} AND user_identity = #{arg1}")
    RefreshToken findByAppIdAndUser(String appId, String userIdentity);

    @Select("SELECT * FROM oauth_refresh_token WHERE refresh_token = #{arg0}")
    RefreshToken findTokenByRefreshToken(String refreshToken);

    @Insert("INSERT INTO oauth_refresh_token (refresh_token, token_id, app_id, user_identity, user_role) " +
            "VALUES (#{refreshToken}, #{tokenId}, #{appId}, #{userIdentity}, #{userRole})")
    int addOne(RefreshToken refreshToken);

    @Update("UPDATE oauth_refresh_token SET token_id = #{arg0}")
    int updateByTokenId(String tokenId);
}