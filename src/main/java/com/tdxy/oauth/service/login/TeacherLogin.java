package com.tdxy.oauth.service.login;

import com.tdxy.oauth.model.bo.LoginResult;
import com.tdxy.oauth.model.bo.User;
import com.tdxy.oauth.model.po.Teacher;
import com.tdxy.oauth.service.TeacherService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service("teacher")
public class TeacherLogin implements LoginStrategy {
    private final TeacherService teacherService;

    @Autowired
    public TeacherLogin(TeacherService teacherService) {
        this.teacherService = teacherService;
    }

    @Override
    public LoginResult login(String username, String password, String role) {
        Teacher teacher = this.teacherService.doLogin(username, password);
        boolean result = teacher == null;
        User user = result ? new User(role, teacher.getTchWorknum()) : null;
        return new LoginResult(result, user);
    }
}
