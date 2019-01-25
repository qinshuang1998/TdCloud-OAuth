package com.tdxy.oauth.model.mapper;

import com.tdxy.oauth.model.entity.Teacher;
import org.apache.ibatis.annotations.Select;

public interface TeacherMapper {
    @Select("SELECT * FROM oauth_user_teacher WHERE tch_name = #{arg0}")
    Teacher findAllByName(String tchName);

    @Select("SELECT * FROM oauth_user_teacher WHERE tch_worknum = #{arg0}")
    Teacher findAllByWorknum(String tchWorknum);

    @Select("SELECT COUNT(*) FROM oauth_user_teacher WHERE tch_name = #{arg0} AND tch_pwd = #{arg1}")
    int findAllByNameAndPwd(String tchName, String tchPwd);
}