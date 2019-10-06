package com.tdxy.oauth.model.dao;

import com.tdxy.oauth.model.po.Teacher;
import com.tdxy.oauth.model.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherDao {
    @Autowired
    private TeacherMapper teacherMapper;

    public Teacher getTeacherByName(String tchName) {
        return this.teacherMapper.findAllByName(tchName);
    }

    public Teacher getTeacherByWorknum(String tchWorknum) {
        return this.teacherMapper.findAllByWorknum(tchWorknum);
    }

    public Teacher getTeacherByNameAndPwd(String tchName, String tchPwd) {
        return this.teacherMapper.findAllByNameAndPwd(tchName, tchPwd);
    }
}
