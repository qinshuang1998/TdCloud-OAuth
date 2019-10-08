package com.tdxy.oauth.service.user;

import com.tdxy.oauth.model.bo.LoginResult;
import com.tdxy.oauth.model.bo.User;
import com.tdxy.oauth.model.po.Member;
import com.tdxy.oauth.model.po.Teacher;
import com.tdxy.oauth.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Objects;

@Service("teacher")
public class TeacherStrategy implements UserStrategy {
    private final TeacherService teacherService;

    @Autowired
    public TeacherStrategy(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public LoginResult login(String username, String password, String role) {
        Teacher teacher = teacherService.doLogin(username, password);
        boolean result = Objects.nonNull(teacher);
        User user = result ? new User(role, teacher.getTchWorknum()) : null;
        return new LoginResult(result, user);
    }

    @Override
    public Member getInfo(String identity) {
        return teacherService.getTeacherByWorknum(identity);
    }
}
