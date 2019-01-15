package com.tdxy.oauth.model.mapper;

import com.tdxy.oauth.model.entity.ZfCookie;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ZfCookieMapper {
    @Insert("INSERT INTO oauth_zf_cookie (stu_number, cookie_hash, cookie_prefix, cookie_value) " +
            "VALUES (#{stuNumber}, #{cookieHash}, #{cookiePrefix}, #{cookieValue})")
    int insertOne(ZfCookie cookie);

    @Select("SELECT * FROM oauth_zf_cookie WHERE stu_number = #{arg0}")
    ZfCookie selectByStuNumber(String stuNumber);

    @Select("SELECT * FROM oauth_zf_cookie WHERE cookie_hash = #{arg0}")
    ZfCookie selectByHash(String cookieHash);

    @Update("UPDATE oauth_zf_cookie SET stu_number = #{stuNumber}, cookie_value = #{cookieValue}")
    int updateOne(ZfCookie cookie);
}