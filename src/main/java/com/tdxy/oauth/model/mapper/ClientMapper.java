package com.tdxy.oauth.model.mapper;

import com.tdxy.oauth.model.entity.Client;
import org.apache.ibatis.annotations.Select;

public interface ClientMapper {
    @Select("SELECT * FROM oauth_client_details WHERE app_id = #{appId}")
    Client selectByAppId(String appId);
}