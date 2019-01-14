package com.tdxy.oauth.model.mapper;

import com.tdxy.oauth.model.entity.ZfCookie;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface ZfCookieMapper {
    @Insert("INSERT INTO oauth_zf_cookie (stu_number, cookie_prefix, cookie_value) " +
            "VALUES (#{stuNumber}, #{cookiePrefix}, #{cookieValue})")
    int insertOne(ZfCookie cookie);

    @Select("SELECT * FROM oauth_zf_cookie WHERE stu_number = #{stuNumber}")
    ZfCookie selectByStuNumber(String stuNumber);

    @Update("UPDATE oauth_zf_cookie SET stu_number = #{stuNumber}, " +
            "cookie_prefix = #{cookiePrefix}, cookie_value = #{cookieValue}")
    int updateOne(ZfCookie cookie);
}