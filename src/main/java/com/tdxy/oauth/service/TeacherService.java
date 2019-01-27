package com.tdxy.oauth.service;

import com.tdxy.oauth.model.entity.Teacher;
import com.tdxy.oauth.model.impl.TeacherImpl;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherImpl teacherImpl;

    @Autowired
    public TeacherService(TeacherImpl teacherImpl) {
        this.teacherImpl = teacherImpl;
    }

    public boolean doLogin(String tchName, String tchPwd) {
        String hashPwd = Md5Crypt.apr1Crypt(tchPwd, "tdxy");
        int count = this.teacherImpl.getTeacherByNameAndPwd(tchName, hashPwd);
        return (count == 1);
    }

    public Teacher getTeacherByName(String tchName) {
        return this.teacherImpl.getTeacherByName(tchName);
    }

    public Teacher getTeacherByWorknum(String tchWorknum) {
        return this.teacherImpl.getTeacherByWorknum(tchWorknum);
    }
}
