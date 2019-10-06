package com.tdxy.oauth.model.mapper;

import com.tdxy.oauth.model.bo.RefreshToken;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface RefreshTokenMapper {
    @Select("SELECT * FROM oauth_refresh_token WHERE app_id = #{appId} AND user_identity = #{userIdentity}")
    RefreshToken findByAppIdAndUser(@Param("appId") String appId, @Param("userIdentity") String userIdentity);

    @Select("SELECT * FROM oauth_refresh_token WHERE refresh_token = #{refreshToken}")
    RefreshToken findTokenByRefreshToken(String refreshToken);

    @Insert("INSERT INTO oauth_refresh_token (refresh_token, token_id, app_id, user_identity, user_role) " +
            "VALUES (#{refreshToken}, #{tokenId}, #{appId}, #{userIdentity}, #{userRole})")
    int addOne(RefreshToken refreshToken);

    @Update("UPDATE oauth_refresh_token SET token_id = #{tokenId} WHERE refresh_token = #{refreshToken}")
    int updateByTokenId(@Param("tokenId") String tokenId, @Param("refreshToken") String refreshToken);
}