package com.tdxy.oauth.model.mapper;

import com.tdxy.oauth.model.bo.ZFCookie;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ZFCookieMapper {
    @Insert("INSERT INTO oauth_zf_cookie (stu_number, cookie_hash, cookie_prefix, cookie_value) " +
            "VALUES (#{stuNumber}, #{cookieHash}, #{cookiePrefix}, #{cookieValue})")
    int insertOne(ZFCookie cookie);

    @Select("SELECT * FROM oauth_zf_cookie WHERE stu_number = #{stuNumber}")
    ZFCookie selectByStuNumber(String stuNumber);

    @Select("SELECT * FROM oauth_zf_cookie WHERE cookie_hash = #{cookieHash}")
    ZFCookie selectByHash(String cookieHash);

    @Update("UPDATE oauth_zf_cookie SET cookie_hash = #{cookieHash}, cookie_value = #{cookieValue} WHERE stu_number = #{stuNumber}")
    int updateOne(ZFCookie cookie);
}