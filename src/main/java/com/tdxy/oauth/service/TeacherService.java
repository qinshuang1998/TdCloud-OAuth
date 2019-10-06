package com.tdxy.oauth.service;

import com.tdxy.oauth.Constant;
import com.tdxy.oauth.model.po.Teacher;
import com.tdxy.oauth.model.dao.TeacherDao;
import org.apache.commons.codec.digest.Md5Crypt;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TeacherService {
    private final TeacherDao teacherDao;

    @Autowired
    public TeacherService(TeacherDao teacherDao) {
        this.teacherDao = teacherDao;
    }

    public Teacher doLogin(String tchName, String tchPwd) {
        String hashPwd = Md5Crypt.apr1Crypt(tchPwd, Constant.Security.MD5_SALT_TCH_PWD);
        return this.teacherDao.getTeacherByNameAndPwd(tchName, hashPwd);
    }

    public Teacher getTeacherByName(String tchName) {
        return this.teacherDao.getTeacherByName(tchName);
    }

    public Teacher getTeacherByWorknum(String tchWorknum) {
        return this.teacherDao.getTeacherByWorknum(tchWorknum);
    }
}
