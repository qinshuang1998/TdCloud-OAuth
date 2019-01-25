package com.tdxy.oauth.model.impl;

import com.tdxy.oauth.model.entity.Teacher;
import com.tdxy.oauth.model.mapper.TeacherMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

@Repository
public class TeacherImpl {
    @Autowired
    private TeacherMapper teacherMapper;

    public Teacher getTeacherByName(String tchName) {
        return this.teacherMapper.findAllByName(tchName);
    }

    public Teacher getTeacherByWorknum(String tchWorknum) {
        return this.teacherMapper.findAllByWorknum(tchWorknum);
    }

    public int getTeacherByNameAndPwd(String tchName, String tchPwd) {
        return this.teacherMapper.findAllByNameAndPwd(tchName, tchPwd);
    }
}
