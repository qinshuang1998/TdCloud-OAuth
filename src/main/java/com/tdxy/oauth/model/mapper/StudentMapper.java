package com.tdxy.oauth.model.mapper;

import com.tdxy.oauth.model.entity.Student;
import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.annotations.Update;

public interface StudentMapper {
    @Insert("INSERT INTO oauth_user_student VALUES (#{stuNumber}, #{stuPwd}, #{stuName}, #{stuSex}, " +
            "#{stuNation}, #{stuMajor}, #{stuIdCard}, #{stuPhone}, #{stuEmail})")
    int insertOne(Student student);

    @Select("SELECT COUNT(*) FROM oauth_user_student WHERE stu_number = #{stuNumber}")
    int findLineByStuNumber(String stuNumber);

    @Select("SELECT stu_name FROM oauth_user_student WHERE stu_number = #{stuNumber}")
    String findNameByStuNumber(String stuNumber);

    @Select("SELECT * FROM oauth_user_student WHERE stu_number = #{stuNumber}")
    Student findAllByStuNumber(String stuNumber);

    @Update("UPDATE oauth_user_student SET stu_number = #{stuNumber}, stu_pwd = #{stuPwd}," +
            "stu_name = #{stuName}, stu_sex = #{stuSex}, stu_nation = #{stuNation}, " +
            "stu_major = #{stuMajor}, stu_id_card = #{stuIdCard}, stu_phone = #{stuPhone}, " +
            "stu_email = #{stuEmail} WHERE stu_number = #{stuNumber}")
    int updateByStuNumber(Student student);
}