package com.tdxy.oauth.model.mapper;

import com.tdxy.oauth.model.po.Teacher;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

public interface TeacherMapper {
    @Select("SELECT * FROM oauth_user_teacher WHERE tch_name = #{tchName}")
    Teacher findAllByName(String tchName);

    @Select("SELECT * FROM oauth_user_teacher WHERE tch_worknum = #{tchWorknum}")
    Teacher findAllByWorknum(String tchWorknum);

    @Select("SELECT * FROM oauth_user_teacher WHERE tch_name = #{tchName} AND tch_pwd = #{tchPwd}")
    Teacher findAllByNameAndPwd(@Param("tchName") String tchName, @Param("tchPwd") String tchPwd);
}